package br.com.alura.forum.controller;

import br.com.alura.forum.model.OpenTopicsOnCategory;
import br.com.alura.forum.repository.OpenTopicsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // allow me to return a page, not only JSON
@RequestMapping("/admin/reports")
@AllArgsConstructor
public class AdminController {

    private OpenTopicsRepository openTopicsRepository;

    @GetMapping("/open-topics-by-category")
    public String showOpenTopicsByCategoryReport(Model model) {
        List<OpenTopicsOnCategory> openTopics =
                openTopicsRepository.findAllByCurrentMonth();
        model.addAttribute("openTopics", openTopics);
        return "report";
    }
}
