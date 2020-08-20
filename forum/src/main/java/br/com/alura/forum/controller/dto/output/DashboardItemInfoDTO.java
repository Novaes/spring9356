package br.com.alura.forum.controller.dto.output;

import br.com.alura.forum.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class DashboardItemInfoDTO {

    private String categoryName;
    private List<String> subcategories;
    private int allTopics;
    private int lastWeekTopics;
    private int unansweredTopics;

    public DashboardItemInfoDTO(Category category, int allTopics, int lastWeekTopics, int unansweredTopics) {
        this.categoryName = category.getName();
        this.subcategories = category.getSubcategoryNames();
        this.allTopics = allTopics;
        this.lastWeekTopics = lastWeekTopics;
        this.unansweredTopics = unansweredTopics;
    }
}
