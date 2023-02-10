package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.Organograma;
import io.sld.riskcomplianceservice.domain.repository.OrganogramaRepository;
import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaArrayDTO;
import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.OrganogramaMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Organograma}.
 */
@Service
@Transactional
public class OrganogramaService {

    private final Logger log = LoggerFactory.getLogger(OrganogramaService.class);

    private final OrganogramaRepository organogramaRepository;

    private final OrganogramaMapper organogramaMapper;

    public OrganogramaService(OrganogramaRepository organogramaRepository, OrganogramaMapper organogramaMapper) {
        this.organogramaRepository = organogramaRepository;
        this.organogramaMapper = organogramaMapper;
    }

    /**
     * Save a organograma.
     *
     * @param organogramaArrayDTO the entity to save.
     * @return the persisted entity.
     */
//    public OrganogramaDTO save(OrganogramaDTO organogramaDTO) {
//        log.debug("Request to save Organograma : {}", organogramaDTO);
//        Organograma organograma = organogramaMapper.toEntity(organogramaDTO);
//        organograma = organogramaRepository.save(organograma);
//        return organogramaMapper.toDto(organograma);
//    }

    public OrganogramaArrayDTO save(OrganogramaArrayDTO organogramaArrayDTO) {
        varrerLista(organogramaArrayDTO, null);
        return organogramaArrayDTO;
    }

    /**
     * Update a organograma.
     *
     * @param organogramaDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganogramaDTO update(OrganogramaDTO organogramaDTO) {
        log.debug("Request to save Organograma : {}", organogramaDTO);
        Organograma organograma = organogramaMapper.toEntity(organogramaDTO);
        organograma = organogramaRepository.save(organograma);
        return organogramaMapper.toDto(organograma);
    }

    /**
     * Partially update a organograma.
     *
     * @param organogramaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrganogramaDTO> partialUpdate(OrganogramaDTO organogramaDTO) {
        log.debug("Request to partially update Organograma : {}", organogramaDTO);

        return organogramaRepository
            .findById(organogramaDTO.getId())
            .map(existingOrganograma -> {
                organogramaMapper.partialUpdate(existingOrganograma, organogramaDTO);

                return existingOrganograma;
            })
            .map(organogramaRepository::save)
            .map(organogramaMapper::toDto);
    }

    /**
     * Get all the organogramas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganogramaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organogramas");
        return organogramaRepository.findAll(pageable).map(organogramaMapper::toDto);
    }

    /**
     * Get one organograma by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrganogramaDTO> findOne(Long id) {
        log.debug("Request to get Organograma : {}", id);
        return organogramaRepository.findById(id).map(organogramaMapper::toDto);
    }

    /**
     * Delete the organograma by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Organograma : {}", id);
        organogramaRepository.deleteById(id);
    }

    public List<OrganogramaDTO> findByEmpresa(Long empresaId) {
        Empresa empresa = new Empresa();
        empresa.setId(empresaId);

        return organogramaRepository.findByEmpresa(empresa).get().stream().map(organogramaMapper::toDto).collect(Collectors.toList());
    }

    public List<OrganogramaArrayDTO> findArrayByEmpresa(Long empresaId) {
        List<OrganogramaDTO> listaOrganograma = findByEmpresa(empresaId);
        List<OrganogramaArrayDTO> nodes = new ArrayList<>();

        OrganogramaArrayDTO organogramaArrayDTO= new OrganogramaArrayDTO();

        organogramaArrayDTO.setId(listaOrganograma.get(0).getId());
        organogramaArrayDTO.setName(listaOrganograma.get(0).getNVarNome());
        organogramaArrayDTO.setChildren(new ArrayList<>());

        criarTree(listaOrganograma, organogramaArrayDTO);
        nodes.add(organogramaArrayDTO);
        return nodes;
    }

    private void criarTree(List<OrganogramaDTO> listaOrganograma, OrganogramaArrayDTO organogramaArrayDTO) {
        List<OrganogramaDTO> novaListaOrganograma = new ArrayList<>();

        for(OrganogramaDTO organogramaDTO : listaOrganograma){
            if(String.valueOf(organogramaArrayDTO.getId()).equals(organogramaDTO.getIdnVarPaiOrganograma())){
                OrganogramaArrayDTO novoOrganogramaArrayDTO = new OrganogramaArrayDTO(organogramaDTO.getId(), organogramaDTO.getNVarNome());
                organogramaArrayDTO.getChildren().add(novoOrganogramaArrayDTO);
            }
        }

        for(OrganogramaArrayDTO filho : organogramaArrayDTO.getChildren()){
            criarTree(listaOrganograma, filho);
        }
    }




    private void varrerLista(OrganogramaArrayDTO organogramaArrayDTO, Long parentId) {
        Organograma organograma = new Organograma();
        organograma.setnVarNome(organogramaArrayDTO.getName());
        organograma.setEmpresa(organogramaArrayDTO.getEmpresa());
        if(parentId != null){
            organograma.setIdnVarPaiOrganograma(String.valueOf(parentId));
            organogramaArrayDTO.setParentId(String.valueOf(parentId));
        }
        organograma = organogramaRepository.save(organograma);
        organogramaArrayDTO.setId(organograma.getId());
        if(organogramaArrayDTO.getChildren() != null){
            for(OrganogramaArrayDTO child : organogramaArrayDTO.getChildren()){
                varrerLista(child, organograma.getId());
            }
        }
    }
}
