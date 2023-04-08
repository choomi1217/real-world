package cho.o.me.blog.article.ui;

import cho.o.me.blog.article.application.ArticleService;
import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.article.ui.request.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    ArticleService articleService;

    @GetMapping("/api/articles")
    public ResponseEntity<List<Article>> articles(@RequestParam ArticleRequest request){
        List<Article> articles = articleService.articles(request);
        return null;
    }
}
