package br.com.alura.forum.dao;

import br.com.alura.forum.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseDao extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);
}
