package br.com.alura.forum.infra;

import br.com.alura.forum.model.Answer;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class NewReplyMailFactory {

    private TemplateEngine templateEngine;

    public MimeMessagePreparator generateMailContent(Answer answer) {
        MimeMessagePreparator mimeMessagePreparator = (mimeMessage) -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setTo(answer.getTopic().getOwnerEmail());
            helper.setSubject("Dúvida respondida em " +   answer.getCreationTime());

            String html = this.generateHtmlContent(answer);
            helper.setText(html, true);
        };

        return mimeMessagePreparator;
    }

    private String generateHtmlContent(Answer answer) {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("topicOwnerName", answer.getTopic().getOwnerName());
        thymeleafContext.setVariable("topicShortDescription", answer.getTopic().getShortDescription());
        thymeleafContext.setVariable("answerAuthor", answer.getOwnerName());
        thymeleafContext.setVariable("answerCreationInstant", getFormattedCreationTime(answer));
        thymeleafContext.setVariable("answerContent", answer.getContent());

        String html = templateEngine.process("email-template.html", thymeleafContext);
        return html;
    }

    private String getFormattedCreationTime(Answer answer) {
        return DateTimeFormatter.ofPattern("kk:mm")
                .withZone(ZoneId.of("America/Sao_Paulo"))
                .format(answer.getCreationTime());
    }
}
