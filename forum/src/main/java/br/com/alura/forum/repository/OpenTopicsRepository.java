package br.com.alura.forum.repository;

import br.com.alura.forum.model.OpenTopicsOnCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface OpenTopicsRepository extends Repository<OpenTopicsOnCategory,Long> {

    void saveAll(Iterable<OpenTopicsOnCategory> topics);

    @Query("select t from OpenTopicsOnCategory t " +
            "where year(t.date) = year(current_date) " +
            "and month(t.date) = month(current_date)")
    List<OpenTopicsOnCategory> findAllByCurrentMonth();
}
