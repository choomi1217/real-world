package cho.o.me.blog.article.domain;

import cho.o.me.blog.member.domain.Member;
import cho.o.me.blog.tag.domain.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;
    private String title;
    private String description;
    private String body;

    @ElementCollection
    @JoinColumn(name="article_id")
    private List<UUID> tagList;

    private String createdAt;
    private String updatedAt;

    private Boolean favorited;
    private Integer favoritesCount;

    private String author;

}
