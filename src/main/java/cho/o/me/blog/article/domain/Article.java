package cho.o.me.blog.article.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    private Long id;
    private String slug;
    private String title;
    private String description;
    private String body;
    private String tagList;
    private String createdAt;
    private String updatedAt;
    private String author;

}
