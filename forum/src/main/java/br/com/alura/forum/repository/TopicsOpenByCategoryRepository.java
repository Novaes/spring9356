package br.com.alura.forum.repository;

import br.com.alura.forum.model.TopicsOpenByCategory;
import org.springframework.data.repository.Repository;

public interface TopicsOpenByCategoryRepository extends Repository<TopicsOpenByCategory, Long> {

    void saveAll(Iterable<TopicsOpenByCategory> unansweredTopics);
}
