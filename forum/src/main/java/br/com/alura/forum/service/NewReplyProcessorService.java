package br.com.alura.forum.service;

import br.com.alura.forum.model.Answer;
import br.com.alura.forum.repository.AnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NewReplyProcessorService {

    private AnswerRepository answerRepository;
    private MailService mailService;

    public void execute(Answer answer) {
        answerRepository.save(answer);
        mailService.sendNewAnswerReply(answer);
    }
}
