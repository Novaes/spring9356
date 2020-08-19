package br.com.alura.forum.dao;

import br.com.alura.forum.model.topic.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TopicDao extends JpaRepository<Topic, Long>, JpaSpecificationExecutor<Topic> {

    @Query("select t from Topic t") // JPQL
    List<Topic> list();

    List<Topic> findAll();

}
