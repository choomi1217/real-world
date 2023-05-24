package cho.o.me.blog.article.ui;

import cho.o.me.blog.article.application.ArticleUsecase;
import cho.o.me.blog.article.ui.request.ArticleCreateRequest;
import cho.o.me.blog.article.ui.request.ArticleFetchRequest;
import cho.o.me.blog.article.ui.response.ArticleListResponse;
import cho.o.me.blog.article.ui.response.ArticleResponse;
import cho.o.me.blog.article.ui.response.ArticleCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<ArticleCreateResponse> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Validated ArticleCreateRequest request) throws URISyntaxException {
        ArticleCreateResponse articleCreateResponse = articleUsecase.create(userDetails.getUsername(), request);
        return ResponseEntity.created(new URI("/api/articles/" + articleCreateResponse.getSlug())).body(articleCreateResponse);
    }

    @GetMapping("/api/articles/{slug}")
    public ResponseEntity<ArticleResponse> article(@PathVariable String slug){
        ArticleResponse articleResponse = articleUsecase.article(slug);
        return ResponseEntity.ok(articleResponse);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<ArticleListResponse> articles(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault Pageable pageable,
            @ModelAttribute ArticleFetchRequest request ){
        return ResponseEntity.ok(articleUsecase.articles(pageable, request, userDetails.getUsername()));
    }

}
