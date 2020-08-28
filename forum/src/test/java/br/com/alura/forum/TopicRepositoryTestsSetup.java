package br.com.alura.forum;

import br.com.alura.forum.model.Category;
import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.OpenTopicsOnCategory;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.repository.TopicRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

public class TopicRepositoryTestsSetup {

    public static TestEntityManager setup(TestEntityManager testEntityManager) {
        Category programacao = testEntityManager
                .persist(new Category("Programação"));
        Category front = testEntityManager
                .persist(new Category("Front-end"));
        Category javaWeb = testEntityManager
                .persist(new Category("Java Web", programacao));
        Category javaScript = testEntityManager
                .persist(new Category("JavaScript", front));
        Course fj27 = testEntityManager
                .persist(new Course("Spring Framework", javaWeb));
        Course fj21 = testEntityManager
                .persist(new Course("Servlet Api e MVC", javaWeb));
        Course js46 = testEntityManager
                .persist(new Course("React", javaScript));
        Topic springTopic = new Topic("Tópico Spring", "Conteúdo do tópico", null, fj27);
        Topic servletTopic = new Topic("Tópico Servlet", "Conteúdo do tópico", null, fj21);
        Topic reactTopic = new Topic("Tópico React", "Conteúdo do tópico", null, js46);
        testEntityManager.persist(springTopic);
        testEntityManager.persist(servletTopic);
        testEntityManager.persist(reactTopic);
        return testEntityManager;
    }
}
