package br.com.alura.forum.model;

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

@RunWith(SpringRunner.class) /*Assim o Spring consegue gerenciar
o JUNIT e injeção de dependencias que precisamos */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TopicRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TopicRepository topicRepository;

//    @Test // test with jnunit
//    public void shouldReturnOpenTopicsByCategory() {
//        List<OpenTopicsOnCategory> openTopics =
//                this.topicRepository.findOpenTopicsOnCategory();
//        Assert.assertEquals(!openTopics.isEmpty(), true);
//    }

    @Test
    public void shouldReturnOpenTopicsByCategory() {

        this.testEntityManager = TopicRepositoryTestsSetup.setup(this.testEntityManager);
        List<OpenTopicsOnCategory> openTopics =
                this.topicRepository.findOpenTopicsOnCategory();
        Assert.assertEquals(!openTopics.isEmpty(), true);
        Assert.assertEquals(openTopics.size() > 2, true);
    }
}
