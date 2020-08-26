package br.com.alura.forum.repository;

import br.com.alura.forum.model.Answer;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AnswerRepository extends Repository<Answer, Long> {

    void save(Answer answer);

    Optional<Answer> findById(Long answerId);
}
