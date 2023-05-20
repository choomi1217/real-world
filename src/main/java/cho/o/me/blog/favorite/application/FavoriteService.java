package cho.o.me.blog.favorite.application;

import cho.o.me.blog.favorite.domain.Favorite;
import cho.o.me.blog.favorite.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public Favorite save(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public List<Favorite> findBySlug(String slug) {
        return favoriteRepository.findBySlug(slug);
    }
}
