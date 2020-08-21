package br.com.alura.forum.dao;

import br.com.alura.forum.model.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserDao extends Repository<User, Long> {

    Optional<User> findByEmail(String email);

}
