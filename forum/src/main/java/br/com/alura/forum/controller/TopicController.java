package br.com.alura.forum.controller;


import br.com.alura.forum.controller.dto.input.NewTopicInputDTO;
import br.com.alura.forum.controller.dto.input.TopicFilterDTO;
import br.com.alura.forum.controller.dto.output.DashboardItemInfoDTO;
import br.com.alura.forum.controller.dto.output.TopicBriefOutputDTO;
import br.com.alura.forum.controller.dto.output.TopicOutputDTO;
import br.com.alura.forum.dao.CategoryRepository;
import br.com.alura.forum.dao.CourseDao;
import br.com.alura.forum.dao.TopicDao;
import br.com.alura.forum.model.Category;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.model.topic.domain.TopicStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import javax.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/topics")
public class TopicController {

    private TopicDao topicDao;
    private CourseDao courseDao;
    private CategoryRepository categoryRepository;

    public TopicController(TopicDao topicDao, CourseDao courseDao, CategoryRepository categoryRepository) {
        this.topicDao = topicDao;
        this.courseDao = courseDao;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public Page<TopicBriefOutputDTO> listTopics(TopicFilterDTO topicFilterDTO,
                                                @PageableDefault(sort = "lastUpdate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Topic> topics = topicDao.findAll(topicFilterDTO.buildCriteria(), pageable);
        return TopicBriefOutputDTO.listFromTopics(topics);
    }

    @GetMapping("dashboard")
    public List<DashboardItemInfoDTO> dash() {
        List<Category> categories = categoryRepository.findByCategoryIsNull();

        return categories.stream().map(category -> {
            Integer allTopics = topicDao.countTopicsByCategoryId(category.getId());
            Instant lastWeekStartDate = Instant.now().minus(7, ChronoUnit.DAYS);
            Integer lastWeek = topicDao.countLastWeekTopicsByCategoryId(category.getId(), lastWeekStartDate);
            Integer notAnswered = topicDao.countTopicsByCategoryIdAndStatus(category.getId(), TopicStatus.NOT_ANSWERED);
            return new DashboardItemInfoDTO(category, allTopics, lastWeek, notAnswered);
        }).collect(Collectors.toList());

    }

    @PostMapping
    public ResponseEntity<TopicOutputDTO> createTopic(@Valid @RequestBody NewTopicInputDTO newTopic,
                                                      @AuthenticationPrincipal User user,
                                                      UriComponentsBuilder uriComponentsBuilder) {

        Topic savedTopic = newTopic.toTopic(courseDao, user);
        topicDao.save(savedTopic);
        URI uri = uriComponentsBuilder.path("api/topics/{id}")
                .buildAndExpand(savedTopic.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicOutputDTO(savedTopic));
    }

}
