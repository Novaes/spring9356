package br.com.alura.forum.service;
import br.com.alura.forum.model.Answer;
import br.com.alura.forum.model.topic.domain.Topic;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ForumMailService {

    private MailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(ForumMailService.class);

    private JavaMailSender javaMailSender;

    TemplateEngine templateEngine = new Context();

    @Async
    public void sendNewReplyMail(Answer answer) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(answer.getTopic().getOwnerEmail());
        simpleMessage.setSubject("Novo comentário em " + answer.getTopic()
                .getShortDescription());
        simpleMessage.setText("Olá " + answer.getTopic().getOwnerName() + "\n\n" +
                "Há uma nova mensagem no fórum! " + answer.getOwnerName() +
                " comentou no tópico: " + answer.getTopic().getShortDescription());
        try {
            mailSender.send(simpleMessage);
        } catch (MailException e) {
            logger.error("Não foi possível enviar email para " + answer.getTopic()
                    .getOwnerEmail(), e.getMessage());
        }
    }

    @Async
    public void sendNewReplyMailMEME(Answer answer) {
        Topic answeredTopic = answer.getTopic();
        MimeMessagePreparator messagePreparator = (mimeMessage) -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(answeredTopic.getOwnerEmail());
            messageHelper.setSubject("Novo comentário em: " + answeredTopic.getShortDescription());
            String messageContent = this.generateNewReplyEmailContent(answer);
            messageHelper.setText(messageContent, true);
        }

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            logger.error("Não foi possível enviar email para " + answer.getTopic()
                    .getOwnerEmail(), e.getMessage());
        }
    }

    public String generateNewReplyEmailContent(Answer answer) {
        Topic answeredTopic = answer.getTopic();
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("topicOwnerName", answeredTopic.getOwnerName());
        thymeleafContext.setVariable("topicShortDescription", answeredTopic.getShortDescription());
        thymeleafContext.setVariable("answerAuthor", answer.getOwnerName());
        thymeleafContext.setVariable("answerCreationInstant", getFormattedCreationTime(answer));
        thymeleafContext.setVariable("answerContent", answer.getContent());
        thymeleafContext.setVariable(template = "templates/email-template.html", thymeleafContext);
        thymeleafContext.setVariable("answerContent", answer.getContent());
        return this.templateEngine.process("email-template.html", thymeleafContext);
    }

    private String getFormattedCreationTime(Answer answer) {
        return DateTimeFormatter.ofPattern("kk:mm")
                .withZone(ZoneId.of("America/Sao_Paulo"))
                .format(answer.getCreationTime());
    }
}