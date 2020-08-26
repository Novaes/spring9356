package br.com.alura.forum.validator;

import br.com.alura.forum.controller.dto.input.NewTopicInputDTO;
import br.com.alura.forum.dao.TopicDao;
import br.com.alura.forum.model.PossibleSpam;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NewTopicInputDTOValidator implements Validator {

    private TopicDao topicDao;
    private User loggedUser;

    public NewTopicInputDTOValidator(TopicDao topicDao, User loggedUser) {
        this.topicDao = topicDao;
        this.loggedUser = loggedUser;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return NewTopicInputDTO.class.isAssignableFrom(aClass);
    }



    @Override
    public void validate(Object newInputTopicDTO, Errors errors) {
        Instant oneHourAgo = Instant.now().minus(1, ChronoUnit.HOURS);
        List<Topic> topicsCreated = topicDao.findByOwnerAndCreationInstantAfterOrderByCreationInstantAsc(loggedUser,
                oneHourAgo);

        PossibleSpam possibleSpam = new PossibleSpam(topicsCreated);

        if(possibleSpam.hasTopicLimitExceeded()) {
            long minutesToNextTopic = possibleSpam.minutesToNextTopic(oneHourAgo);
            String defaultMessage = "Você atingiu o númeor máximo permitido para criar tópico na última hora";
            errors.reject("user.limit.exceeded", new Object[]{minutesToNextTopic}, defaultMessage);
        }

    }
}
