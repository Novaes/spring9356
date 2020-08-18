package br.com.alura.forum.controller.dto.output;

import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.model.topic.domain.TopicStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TopicBriefOutputDTO {

    private Long id;
    private String shortDescription;
    private Long secondsSinceLastUpdate;
    private String ownerName;
    private String courseName;
    private String subcategoryName;
    private String categoryName;
    private Integer numberOfResponses;
    private Boolean solved;

    public TopicBriefOutputDTO(Topic topic) {
        this.id = topic.getId();
        this.shortDescription = topic.getShortDescription();
        this.secondsSinceLastUpdate = getSecondsSince(topic.getLastUpdate());
        this.ownerName = topic.getOwner().getName();
        this.courseName = topic.getCourse().getName();
        this.subcategoryName = topic.getCourse().getSubcategoryName();
        this.categoryName = topic.getCourse().getCategoryName();
        this.numberOfResponses = topic.getNumberOfAnswers();
        this.solved = TopicStatus.SOLVED.equals(topic.getStatus());

    }

    public static List<TopicBriefOutputDTO> listFromTopics(List<Topic> topics) {
        return topics.stream().map(TopicBriefOutputDTO::new).collect(Collectors.toList());
    }
    private static Long getSecondsSince(Instant lastUpdate) {
        return Duration.between(lastUpdate, Instant.now()).get(ChronoUnit.SECONDS);
    }
}
