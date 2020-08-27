package br.com.alura.forum.service;

import br.com.alura.forum.model.Answer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class ThymeleafService {

    private TemplateEngine templateEngine;



}
