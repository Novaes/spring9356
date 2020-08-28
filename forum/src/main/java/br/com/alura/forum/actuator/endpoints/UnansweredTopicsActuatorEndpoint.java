package br.com.alura.forum.actuator.endpoints;

import br.com.alura.forum.model.TopicsOpenByCategory;
import br.com.alura.forum.model.topic.domain.TopicStatus;
import br.com.alura.forum.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.List;

@Endpoint(id="open-topics-by-category")
@Component
@AllArgsConstructor
public class UnansweredTopicsActuatorEndpoint {

    private TopicRepository topicRepository;

    @ReadOperation
    public List<TopicsOpenByCategory> execute() {
        return topicRepository.findOpenTopicsByCategory(TopicStatus.NOT_ANSWERED);
    }
}
