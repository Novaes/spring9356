package br.com.alura.forum.controller.dto.output;

import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.model.topic.domain.TopicStatus;
import lombok.Getter;

import java.time.Instant;

@Getter
public class TopicOutputDTO {

    private Long id;
    private String shortDescription;
    private String content;
    private TopicStatus status;
    private int numberOfResponses;
    private Instant creationInstant;
    private Instant lastUpdate;

    private String ownerName;
    private String courseName;
    private String subcategoryName;
    private String categoryName;


    public TopicOutputDTO(Topic topic) {
        this.id = topic.getId();
        this.shortDescription = topic.getShortDescription();
        this.content = topic.getContent();
        this.status = topic.getStatus();
        this.numberOfResponses = topic.getNumberOfAnswers();
        this.creationInstant = topic.getCreationInstant();
        this.lastUpdate = topic.getLastUpdate();
        this.ownerName = topic.getOwner().getName();
        this.courseName = topic.getCourse().getName();
        this.subcategoryName = topic.getCourse().getSubcategoryName();
        this.categoryName = topic.getCourse().getCategoryName();
    }

}
