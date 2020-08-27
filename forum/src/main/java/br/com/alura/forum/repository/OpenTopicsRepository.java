package br.com.alura.forum.repository;

import br.com.alura.forum.model.OpenTopicsOnCategory;
import org.springframework.data.repository.Repository;

public interface OpenTopicsRepository extends Repository<OpenTopicsOnCategory,Long> {

    void saveAll(Iterable<OpenTopicsOnCategory> topics);
}
