package br.com.alura.forum.repository;

import br.com.alura.forum.model.TopicsOpenByCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TopicsOpenByCategoryRepository extends Repository<TopicsOpenByCategory, Long> {

    void saveAll(Iterable<TopicsOpenByCategory> unansweredTopics);

    List<TopicsOpenByCategory> findAll();

    @Query("select t from TopicsOpenByCategory t " +
            "where year(t.createdAt) = year(current_date)" +
            " and month(t.createdAt) = month(current_date)")
    List<TopicsOpenByCategory> findAllByCurrentMonth();

}
