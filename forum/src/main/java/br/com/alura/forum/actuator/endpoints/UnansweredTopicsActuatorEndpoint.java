package br.com.alura.forum.actuator.endpoints;

import br.com.alura.forum.model.OpenTopicsOnCategory;
import br.com.alura.forum.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Endpoint(id="open-topics-by-category")
public class UnansweredTopicsActuatorEndpoint {
    private TopicRepository topicRepository;

    @ReadOperation
    public List<OpenTopicsOnCategory> execute() {
        return topicRepository.findOpenTopicsOnCategory();
    }
}
