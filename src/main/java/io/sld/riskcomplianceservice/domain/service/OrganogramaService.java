package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.Organograma;
import io.sld.riskcomplianceservice.domain.repository.OrganogramaRepository;
import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaTreeDTO;
import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.OrganogramaMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
     * @param organogramaTreeDTO the entity to save.
     * @return the persisted entity.
     */
//    public OrganogramaDTO save(OrganogramaDTO organogramaDTO) {
//        log.debug("Request to save Organograma : {}", organogramaDTO);
//        Organograma organograma = organogramaMapper.toEntity(organogramaDTO);
//        organograma = organogramaRepository.save(organograma);
//        return organogramaMapper.toDto(organograma);
//    }

    public OrganogramaTreeDTO save(OrganogramaTreeDTO organogramaTreeDTO) {
        deletarTree(organogramaTreeDTO.getEmpresa());
        salvarTree(organogramaTreeDTO, null);
        return organogramaTreeDTO;
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

    public List<OrganogramaTreeDTO> findTreeByEmpresa(Long empresaId) {
        List<OrganogramaDTO> listaOrganograma = findByEmpresa(empresaId);
        List<OrganogramaTreeDTO> nodes = new ArrayList<>();

        OrganogramaTreeDTO organogramaTreeDTO = new OrganogramaTreeDTO();
        Empresa empresa = new Empresa();
        empresa.setId(listaOrganograma.get(0).getEmpresa().getId());

//        organogramaTreeDTO.setId(listaOrganograma.get(0).getId());
        organogramaTreeDTO.setId(listaOrganograma.get(0).getId());
        organogramaTreeDTO.setName(listaOrganograma.get(0).getNVarNome());
        organogramaTreeDTO.setEmpresa(empresa);
        organogramaTreeDTO.setChildren(new ArrayList<>());

        criarTree(listaOrganograma, organogramaTreeDTO);
        nodes.add(organogramaTreeDTO);
        return nodes;
    }

    private void criarTree(List<OrganogramaDTO> listaOrganograma, OrganogramaTreeDTO organogramaTreeDTO) {
        for(OrganogramaDTO organogramaDTO : listaOrganograma){
            if(String.valueOf(organogramaTreeDTO.getId()).equals(organogramaDTO.getIdnVarPaiOrganograma())){
//                OrganogramaTreeDTO novoOrganogramaTreeDTO = new OrganogramaTreeDTO(organogramaDTO.getId(), organogramaDTO.getNVarNome(), organogramaDTO.getEmpresa().getId());
                OrganogramaTreeDTO novoOrganogramaTreeDTO = new OrganogramaTreeDTO(organogramaDTO.getId(), organogramaDTO.getNVarNome(), organogramaDTO.getEmpresa().getId());
                organogramaTreeDTO.getChildren().add(novoOrganogramaTreeDTO);
            }
        }

        for(OrganogramaTreeDTO filho : organogramaTreeDTO.getChildren()){
            criarTree(listaOrganograma, filho);
        }
    }

//    private void salvarTree(OrganogramaTreeDTO organogramaTreeDTO, Long parentId) {
//        Organograma organograma = new Organograma();
//        organograma.setnVarNome(organogramaTreeDTO.getName());
//        organograma.setEmpresa(organogramaTreeDTO.getEmpresa());
//        organograma.setIdnVarOrganograma(UUID.randomUUID());
//        if(parentId != null){
//            organograma.setIdnVarPaiOrganograma(String.valueOf(parentId));
//            organogramaTreeDTO.setParentId(String.valueOf(parentId));
//        }
//
//        organograma = organogramaRepository.save(organograma);
//
//        organogramaTreeDTO.setId(organograma.getId());
//        if(organogramaTreeDTO.getChildren() != null){
//            for(OrganogramaTreeDTO child : organogramaTreeDTO.getChildren()){
//                salvarTree(child, organograma.getId());
//            }
//        }
//    }
    private void salvarTree(OrganogramaTreeDTO organogramaTreeDTO, Long parentId) {
        Organograma organograma = new Organograma();
        organograma.setnVarNome(organogramaTreeDTO.getName());
        organograma.setEmpresa(organogramaTreeDTO.getEmpresa());
        organograma.setIdnVarOrganograma(UUID.randomUUID());
        if(parentId != null){
            organograma.setIdnVarPaiOrganograma(String.valueOf(parentId));
            organogramaTreeDTO.setParentId(String.valueOf(parentId));
        }

        organograma = organogramaRepository.save(organograma);

        organogramaTreeDTO.setId(organograma.getId());
        if(organogramaTreeDTO.getChildren() != null){
            for(OrganogramaTreeDTO child : organogramaTreeDTO.getChildren()){
                salvarTree(child, organograma.getId());
            }
        }
    }

    private void deletarTree(Empresa empresa) {
        organogramaRepository.deleteCustomByEmpresa(empresa.getId());
    }
}
