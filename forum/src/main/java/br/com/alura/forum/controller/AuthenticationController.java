package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.input.LoginInputDTO;
import br.com.alura.forum.controller.dto.output.TokenOutputDTO;
import br.com.alura.forum.model.User;
import br.com.alura.forum.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenOutputDTO> login(@RequestBody LoginInputDTO loginInput) {

        Authentication authenticate = authenticationManager.authenticate(loginInput.tokenCredentials());
        User user = (User) authenticate.getPrincipal();
        return ResponseEntity.ok(new TokenOutputDTO("Bearer", tokenService.createToken(user)));



    }
}
