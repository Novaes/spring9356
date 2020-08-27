package br.com.alura.forum.service;

import br.com.alura.forum.infra.NewReplyMailFactory;
import br.com.alura.forum.model.Answer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MailService {

    private JavaMailSender mailSender;
    private NewReplyMailFactory newReplyMailFactory;

    @Async
    public void sendNewAnswerReply(Answer answer) {

        try {
            mailSender.send(newReplyMailFactory.generateMailContent(answer));
        } catch (MailException ex) {
            log.error("Não foi possível enviar email para topico {} ex {} ", answer.getTopic().getShortDescription(), ex.getMessage());
        }

    }



}
