package br.com.alura.forum.controller.dto.output;

import br.com.alura.forum.model.Answer;
import lombok.Getter;

import java.time.Instant;

@Getter
public class AnswerOutputDTO {

    private Long id;
    private String content;
    private Instant creationTime;
    private Boolean solution;
    private String ownerName;

    public AnswerOutputDTO(Answer answer) {
        this.id = answer.getId();
        this.ownerName = answer.getOwner().getName();
        this.content = answer.getContent();
        this.solution = answer.isSolution();
        this.creationTime = answer.getCreationTime();
    }
}
