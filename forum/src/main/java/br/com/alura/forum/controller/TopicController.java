package br.com.alura.forum.controller;


import br.com.alura.forum.controller.dto.output.TopicBriefOutputDTO;
import br.com.alura.forum.dao.TopicDao;
import br.com.alura.forum.model.Category;
import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.User;
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

    @ResponseBody
    @GetMapping(value = "/api/topics", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TopicBriefOutputDTO> listTopics() {
        Category subcategory = new Category("Java", new Category("Programação"));
        Course javaComSpring = new Course("Java com Spring", subcategory);
        User user = new User("Maroto", "marotinho@gmail.com", "123456");
        Topic topic = new Topic("Problema ao configurar o spring", "erro ao fazer o start da aplicação", user, javaComSpring);

        return TopicBriefOutputDTO.listFromTopics(topicDao.findAll());

    }
}
