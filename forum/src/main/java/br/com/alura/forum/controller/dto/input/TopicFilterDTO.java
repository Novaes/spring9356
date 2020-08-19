package br.com.alura.forum.controller.dto.input;


import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.model.topic.domain.TopicStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

@Getter
@Setter
public class TopicFilterDTO {

    private String categoryName;
    private TopicStatus status;
    private String username;


    public Specification<Topic> buildCriteria() {
     return ((root, criteriaQuery, criteriaBuilder) -> {

         ArrayList<Predicate> predicates = new ArrayList<>();

         if (status != null) {
             predicates.add(criteriaBuilder.equal(root.get("status"), status));
         }

         if (categoryName != null) {
             Predicate verificaSeCategoriaEhIgualAhPassada = criteriaBuilder.equal(root.get("course").get("subcategory").get("name"), categoryName);
             predicates.add(verificaSeCategoriaEhIgualAhPassada);
         }

         if (username != null) {
             Predicate verificaSeCategoriaEhIgualAhPassada = criteriaBuilder.equal(root.get("owner").get("name"), username);
             predicates.add(verificaSeCategoriaEhIgualAhPassada);
         }

         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

     });
    }
}
