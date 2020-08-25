package br.com.alura.forum.controller.dto.input;

import br.com.alura.forum.dao.CourseDao;
import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewTopicInputDTO {
    public String shortDescription;
    public String content;
    public String courseName;

    public Topic toTopic(CourseDao courseDao, User loggedUser) {
        Course course = courseDao.findByName(this.courseName)
                .orElseThrow(() -> new IllegalArgumentException("curso não encontrado"));
        return new Topic(this.getShortDescription(), this.getContent(), loggedUser, course);
    }
}
