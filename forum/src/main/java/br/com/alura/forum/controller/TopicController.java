package br.com.alura.forum.controller;


import br.com.alura.forum.controller.dto.input.NewAnswerInputDTO;
import br.com.alura.forum.controller.dto.input.NewTopicInputDTO;
import br.com.alura.forum.controller.dto.input.TopicFilterDTO;
import br.com.alura.forum.controller.dto.output.AnswerOutputDTO;
import br.com.alura.forum.controller.dto.output.DashboardItemInfoDTO;
import br.com.alura.forum.controller.dto.output.TopicBriefOutputDTO;
import br.com.alura.forum.controller.dto.output.TopicOutputDTO;
import br.com.alura.forum.repository.AnswerRepository;
import br.com.alura.forum.repository.CategoryRepository;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.exception.ResourceNotFound;
import br.com.alura.forum.model.Answer;
import br.com.alura.forum.model.Category;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.model.topic.domain.TopicStatus;
import br.com.alura.forum.service.TopicService;
import br.com.alura.forum.validator.NewTopicInputDTOValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/topics")
@AllArgsConstructor
public class TopicController {

    private TopicRepository topicRepository;
    private CourseRepository courseRepository;
    private CategoryRepository categoryRepository;
    private AnswerRepository answerRepository;
    private TopicService topicService;

    @GetMapping
    public Page<TopicBriefOutputDTO> listTopics(TopicFilterDTO topicFilterDTO,
                                                @PageableDefault(sort = "lastUpdate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Topic> topics = topicRepository.findAll(topicFilterDTO.buildCriteria(), pageable);
        return TopicBriefOutputDTO.listFromTopics(topics);
    }

    @GetMapping("dashboard")
    public List<DashboardItemInfoDTO> dash() {
        List<Category> categories = categoryRepository.findByCategoryIsNull();

        return categories.stream().map(category -> {
            Integer allTopics = topicRepository.countTopicsByCategoryId(category.getId());
            Instant lastWeekStartDate = Instant.now().minus(7, ChronoUnit.DAYS);
            Integer lastWeek = topicRepository.countLastWeekTopicsByCategoryId(category.getId(), lastWeekStartDate);
            Integer notAnswered = topicRepository.countTopicsByCategoryIdAndStatus(category.getId(), TopicStatus.NOT_ANSWERED);
            return new DashboardItemInfoDTO(category, allTopics, lastWeek, notAnswered);
        }).collect(Collectors.toList());

    }

    @PostMapping
    public ResponseEntity<TopicOutputDTO> createTopic(@Valid @RequestBody NewTopicInputDTO newTopic,
                                                      @AuthenticationPrincipal User user,
                                                      UriComponentsBuilder uriComponentsBuilder) {

        Topic savedTopic = newTopic.toTopic(courseRepository, user);
        topicRepository.save(savedTopic);
        URI uri = uriComponentsBuilder.path("api/topics/{id}")
                .buildAndExpand(savedTopic.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicOutputDTO(savedTopic));
    }

    @GetMapping("{id}")
    public ResponseEntity<TopicOutputDTO> findById(@PathVariable("id") Long topicId) {
        Topic topic = topicService.findById(topicId);
        return ResponseEntity.ok(new TopicOutputDTO(topic));
    }

    @GetMapping("{idDoTopico}/answers/{idAnswer}")
    public ResponseEntity<AnswerOutputDTO> findAnswerById(@PathVariable("idDoTopico") Long topicId,
                                                         @PathVariable("idAnswer") Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new ResourceNotFound("answer not found"));
        return ResponseEntity.ok(new AnswerOutputDTO(answer));
    }

    @InitBinder("newTopicInputDTO")
    public void initBinder(WebDataBinder binder, @AuthenticationPrincipal User user) {
        binder.addValidators(new NewTopicInputDTOValidator(topicRepository, user));
    }
}

