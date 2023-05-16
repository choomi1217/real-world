package cho.o.me.blog.article.repository;

import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.article.ui.response.ArticleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    Optional<Article> findBySlug(String slug);
}
