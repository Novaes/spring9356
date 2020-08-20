package br.com.alura.forum.controller;


import br.com.alura.forum.controller.dto.input.TopicFilterDTO;
import br.com.alura.forum.controller.dto.output.TopicBriefOutputDTO;
import br.com.alura.forum.dao.TopicDao;
import br.com.alura.forum.model.topic.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

    private TopicDao topicDao;

    public TopicController(TopicDao topicDao) {
        this.topicDao = topicDao;
    }


    @ResponseBody
    @GetMapping(value = "/api/topics", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<TopicBriefOutputDTO> listTopics(TopicFilterDTO topicFilterDTO,
                                                @PageableDefault(sort = "lastUpdate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Topic> topics = topicDao.findAll(topicFilterDTO.buildCriteria(), pageable);
        return TopicBriefOutputDTO.listFromTopics(topics);
    }
}
