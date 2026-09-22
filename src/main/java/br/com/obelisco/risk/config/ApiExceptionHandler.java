package br.com.obelisco.risk.config;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail validation(MethodArgumentNotValidException ex) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Existem campos inválidos na requisição.");
        p.setTitle("Falha de validação"); p.setType(URI.create("urn:risk:validation"));
        p.setProperty("errors", ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage() == null ? "inválido" : e.getDefaultMessage(), (a,b) -> a)));
        return p;
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    ProblemDetail conflict(DataIntegrityViolationException ex) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "O registro viola uma restrição de integridade ou ainda possui vínculos.");
        p.setTitle("Conflito de dados"); p.setType(URI.create("urn:risk:data-conflict")); return p;
    }
}
