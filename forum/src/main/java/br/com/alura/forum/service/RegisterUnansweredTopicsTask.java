package br.com.alura.forum.service;

import br.com.alura.forum.model.TopicsOpenByCategory;
import br.com.alura.forum.model.topic.domain.TopicStatus;
import br.com.alura.forum.repository.CategoryRepository;
import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.repository.TopicsOpenByCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RegisterUnansweredTopicsTask {

    private CategoryRepository categoryRepository;
    private TopicRepository topicRepository;
    private TopicsOpenByCategoryRepository topicsOpenByCategoryRepository;

    public void anotherWay() {
        log.info("running register unanswered topics");
        List<TopicsOpenByCategory> unansweredTopics = categoryRepository.findByCategoryIsNull().stream().map(category -> {
            Integer quantity = topicRepository.countTopicsByCategoryIdAndStatus(category.getId(), TopicStatus.NOT_ANSWERED);
            return new TopicsOpenByCategory(category.getName(), quantity, new Date());
        }).collect(Collectors.toList());

        topicsOpenByCategoryRepository.saveAll(unansweredTopics);
    }

    @Transactional
    @Scheduled(fixedRate = 10000)
    public void registerOpenTopics() {
        log.info("running register unanswered topics");
        List<TopicsOpenByCategory> topics = topicRepository.findOpenTopicsByCategory(TopicStatus.NOT_ANSWERED);
        topicsOpenByCategoryRepository.saveAll(topics);
    }
}
