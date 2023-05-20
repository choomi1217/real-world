package cho.o.me.blog.favorite.repository;

import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.favorite.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findBySlug(String slug);
}
