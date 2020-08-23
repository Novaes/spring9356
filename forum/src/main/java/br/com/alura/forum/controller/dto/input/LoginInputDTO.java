package br.com.alura.forum.controller.dto.input;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class LoginInputDTO {

    private String email;
    private String password;

    public UsernamePasswordAuthenticationToken tokenCredentials() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }
}
