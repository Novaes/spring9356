package br.com.alura.forum.controller.dto.input;

import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NewTopicInputDTO {

    @NotBlank
    @Size(min = 20, max = 250)
    public String shortDescription; //"a"

    @NotBlank
    @Size(min = 20, max = 250)
    public String content;

    @NotBlank
    @Size(min = 20, max = 100)
    public String courseName;

    public Topic toTopic(CourseRepository courseRepository, User loggedUser) {
        Course course = courseRepository.findByName(this.courseName)
                .orElseThrow(() -> new IllegalArgumentException("curso não encontrado"));
        return new Topic(this.getShortDescription(), this.getContent(), loggedUser, course);
    }
}
