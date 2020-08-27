package br.com.alura.forum.task;

import br.com.alura.forum.model.OpenTopicsOnCategory;
import br.com.alura.forum.repository.OpenTopicsRepository;
import br.com.alura.forum.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RegisterUnansweredTopicsTask {

    private TopicRepository topicRepository;
    private OpenTopicsRepository openTopicsRepository;

    // https://crontab.guru/#5_0_*_*_*
    @Scheduled(cron = "0 0 20 * * *")
    public void execute() {
        List<OpenTopicsOnCategory> topics =
                topicRepository.findOpenTopicsOnCategory();
        openTopicsRepository.saveAll(topics);
    }
}
