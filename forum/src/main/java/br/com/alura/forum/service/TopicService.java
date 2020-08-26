package br.com.alura.forum.service;

import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.exception.ResourceNotFound;
import br.com.alura.forum.model.topic.domain.Topic;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TopicService {

    private TopicRepository topicRepository;

    public Topic findById(Long topicId) {
        return topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFound("topic not found"));
    }
}
