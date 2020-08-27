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

@Entity
@Getter
@Setter
public class TopicsOpenByCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @deprecated hibernate only
     */
    public TopicsOpenByCategory() {}

    private int quantity;
    private String categoryName;
    private LocalDate createdAt;

    public TopicsOpenByCategory(String categoryName, Number quantity, Date createdAt) {
        this.categoryName = categoryName;
        this.quantity = quantity.intValue();
        this.createdAt = createdAt.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
    }
}