package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.input.NewAnswerInputDTO;
import br.com.alura.forum.controller.dto.output.AnswerOutputDTO;
import br.com.alura.forum.model.Answer;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.repository.AnswerRepository;
import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.service.ForumMailService;
import br.com.alura.forum.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/topics")
@AllArgsConstructor
public class AnswerController {

    private TopicRepository topicRepository;
    private AnswerRepository answerRepository;
    private TopicService topicService;
    private ForumMailService forumMailService;


    @PostMapping("{idDoTopico}/answers")
    public ResponseEntity<AnswerOutputDTO> createAnswer(@PathVariable("idDoTopico") Long topicId,
                                                        @Valid @RequestBody NewAnswerInputDTO input,
                                                        @AuthenticationPrincipal User loggedUser,
                                                        UriComponentsBuilder uriBuilder) {
        Topic topic = topicService.findById(topicId);
        Answer answer = input.toAnswer(topic, loggedUser);
        answerRepository.save(answer);
        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("idDoTopico", topic.getId());
        pathVariables.put("answerId", answer.getId());

        forumMailService.sendNewReplyMail(answer);

        URI uri = uriBuilder.path("api/topics/{idDoTopico}/answers/{answerId}")
                .buildAndExpand(pathVariables).toUri();


        return ResponseEntity.created(uri).body(new AnswerOutputDTO(answer));
    }

}
