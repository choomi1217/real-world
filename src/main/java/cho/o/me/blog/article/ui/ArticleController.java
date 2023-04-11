package cho.o.me.blog.article.ui;

import cho.o.me.blog.article.application.ArticleService;
import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.article.ui.request.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    ArticleService articleService;

    @GetMapping("/api/articles")
    public ResponseEntity<Page<Article>> articles(@RequestParam ArticleRequest request) {
        return ResponseEntity.ok(articleService.articles(request));
    }
}
