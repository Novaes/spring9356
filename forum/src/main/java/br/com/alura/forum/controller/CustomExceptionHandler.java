package br.com.alura.forum.controller;


import br.com.alura.forum.controller.dto.output.ErrorOutputDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "br.com.alura.forum.controller")
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({Exception.class, NullPointerException.class})
    public ResponseEntity erro500(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(new ErrorOutputDTO("internal-error", "Unexpected error happens"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity bad_credentials(AuthenticationException ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorOutputDTO("credentials", "Revise suas credenciais"));
    }

}
