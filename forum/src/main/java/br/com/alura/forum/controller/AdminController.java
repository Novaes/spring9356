package br.com.alura.forum.controller;

import br.com.alura.forum.repository.TopicsOpenByCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reports")
@AllArgsConstructor
public class AdminController {

    private TopicsOpenByCategoryRepository repository;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("openTopics", repository.findAllByCurrentMonth());
        return "report";
    }

}
