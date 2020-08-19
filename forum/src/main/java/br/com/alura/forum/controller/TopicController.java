package br.com.alura.forum.controller;


import br.com.alura.forum.controller.dto.input.TopicFilterDTO;
import br.com.alura.forum.controller.dto.output.TopicBriefOutputDTO;
import br.com.alura.forum.dao.TopicDao;
import br.com.alura.forum.model.topic.domain.Topic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicController {

    private TopicDao topicDao; // field injection


    //injection by constructor
    // SOLID
    public TopicController(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    // 1 - sem nenhum filtro = retorna todos os registros check
    // 2 - categoryName=Java = retornar com o filtro de categoria
    // 3 - status=Solved = retorna todos os resolvidos
    // 4 - categoryName=Java e status Solved =

    @ResponseBody
    @GetMapping(value = "/api/topics", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TopicBriefOutputDTO> listTopics(TopicFilterDTO topicFilterDTO) {

        List<Topic> topics = topicDao.findAll(topicFilterDTO.buildCriteria());
        return TopicBriefOutputDTO.listFromTopics(topics);

    }
}
