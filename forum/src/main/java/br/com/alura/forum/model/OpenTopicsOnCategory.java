package br.com.alura.forum.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


@Setter
@Getter
@Entity
public class OpenTopicsOnCategory {

    public OpenTopicsOnCategory(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    private int topicCount;
    private LocalDate date;

    public OpenTopicsOnCategory(String categoryName, Number topicCount, Date currDate) {
        this.categoryName = categoryName;
        this.topicCount = topicCount.intValue();
        this.date = currDate.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
