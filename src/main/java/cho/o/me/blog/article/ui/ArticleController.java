package cho.o.me.blog.article.ui;

import cho.o.me.blog.article.application.ArticleService;
import cho.o.me.blog.article.application.ArticleUsecase;
import cho.o.me.blog.article.ui.request.CreateArticleRequest;
import cho.o.me.blog.article.ui.response.ArticleResponse;
import cho.o.me.blog.article.ui.response.CreateArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleUsecase articleUsecase;

    @PostMapping("/api/articles")
    public ResponseEntity<CreateArticleResponse> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Validated CreateArticleRequest request) throws URISyntaxException {
        CreateArticleResponse createArticleResponse = articleUsecase.create(userDetails.getUsername(), request);
        return ResponseEntity.created(new URI("/api/articles/" + createArticleResponse.getSlug())).body(createArticleResponse);
    }

    @GetMapping("/api/articles/{slug}")
    public ResponseEntity<ArticleResponse> article(@PathVariable String slug){
        ArticleResponse articleResponse = articleUsecase.findBySlugWithoutAuthenticate(slug);
        return ResponseEntity.ok(articleResponse);
    }

}
