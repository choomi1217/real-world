package cho.o.me.blog.favorite.ui;

import cho.o.me.blog.article.application.ArticleUsecase;
import cho.o.me.blog.article.ui.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final ArticleUsecase articleUsecase;

    @GetMapping("/api/articles/{slug}/favorite")
    public ResponseEntity<ArticleResponse> favorite(@AuthenticationPrincipal UserDetails userDetails,  @PathVariable String slug){
        ArticleResponse articleResponse = articleUsecase.favorite(userDetails.getUsername(), slug);
        return ResponseEntity.ok(articleResponse);
    }

    @DeleteMapping("/api/articles/{slug}/favorite")
    public void unfavorite(@AuthenticationPrincipal UserDetails userDetails,  @PathVariable String slug){

    }

}
