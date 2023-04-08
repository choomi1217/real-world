package cho.o.me.blog.article.repository;


import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.article.ui.request.ArticleRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ArticleSpecifications {

    public static Specification<Article> search(ArticleRequest request){

        String tag = request.getTag();
        String author = request.getAuthor();
        String favorite = request.getFavorite();

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(tag != null){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("tag")), "%" + tag.toLowerCase() + "%"));
            }
            if (author != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
            }
            if(favorite != null){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("favorite")), "%" + favorite.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
