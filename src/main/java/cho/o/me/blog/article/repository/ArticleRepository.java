package cho.o.me.blog.article.repository;

import cho.o.me.blog.article.domain.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findBySlug(String slug);

    @Query("select Article from Article " +
            "where(:author is null or author = :author) " +
            "and (:tag is null or :tag member of tagList) " +
            "and (:favorited is null or :favorited member of favoritedList)")
    List<Article> findArticlesBySearch(@Param("author") String author,
                       @Param("tag") String tag,
                       @Param("favorited") String favorited,
                       Pageable pageable);
}
