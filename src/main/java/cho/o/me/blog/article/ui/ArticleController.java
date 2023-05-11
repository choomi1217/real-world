package cho.o.me.blog.article.ui;

import cho.o.me.blog.article.application.ArticleService;
import cho.o.me.blog.article.ui.request.ArticleRequest;
import cho.o.me.blog.article.ui.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public ResponseEntity<ArticleResponse> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Validated ArticleRequest request) {
        return ResponseEntity.ok(new ArticleResponse(articleService.articles(userDetails.getUsername(), request)));
    }

}
