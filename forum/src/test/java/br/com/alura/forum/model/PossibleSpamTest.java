package br.com.alura.forum.model;

import br.com.alura.forum.model.topic.domain.Topic;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PossibleSpamTest {

    @Test
    public void naoDeveExcederOLimiteConfigurado() {
        List<Topic> topics = Arrays.asList(new Topic("curso de teste",
                        "teste1", null, null),
                new Topic("curso de teste",
                        "teste1", null, null));
        PossibleSpam possibleSpam = new PossibleSpam(topics);
        Assert.assertFalse(possibleSpam.hasTopicLimitExceeded());

    }

    @Test
    public void deveExcederOLimiteConfigurado() {
        List<Topic> topics = Arrays.asList(new Topic("curso de teste",
                        "teste1", null, null),
                new Topic("curso de teste",
                        "teste1", null, null),
                new Topic("curso de teste",
                        "teste1", null, null),
                new Topic("curso de teste",
                        "teste1", null, null));
        PossibleSpam possibleSpam = new PossibleSpam(topics);
        Assert.assertTrue(possibleSpam.hasTopicLimitExceeded());

    }
}
