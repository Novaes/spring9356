package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.input.NewAnswerInputDTO;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.repository.UserRepository;
import br.com.alura.forum.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.util.UriTemplate;
import javax.transaction.Transactional;

import java.net.URI;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AllArgsConstructor
public class AnswerControllerTests {

    private static final String ENDPOINT = "/api/topics/{topicId}/answers";

    private TopicRepository topicRepository;
    private UserRepository userRepository;
    private TokenService tokenService;
    private AuthenticationManager authManager;

    private Long topicId;
    private String jwt;

    private MockMvc mockMvc;

    public void setup() throws RuntimeException {
        String rawPassword = "123456";
        User user = new User("Aluno da Alura", "aluno@gmail.com",
                new BCryptPasswordEncoder().encode(rawPassword));
        this.userRepository.save(user);

        Topic topic = new Topic("Descrição do Tópico", "Conteúdo do Tópico",
                user, null);

        this.topicRepository.save(topic);
        this.topicId = topic.getId();

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), rawPassword));
        this.jwt =this.tokenService.createToken(user);
    }

    @Test
    public void shouldProcessSuccessfullyNewAnswerRequest() throws Exception {
        URI uri = new UriTemplate(ENDPOINT).expand(this.topicId);
        NewAnswerInputDTO inputDto = new NewAnswerInputDTO();
        inputDto.setContent("Não consigo subir o servidor");
        MockHttpServletRequestBuilder request = post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + this.jwt)
                .content(new ObjectMapper().writeValueAsString(inputDto));
        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content()
                        .string(containsString(inputDto.getContent())));
    }
}



