package cho.o.me.blog.article.application;

import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.article.repository.ArticleRepository;
import cho.o.me.blog.article.ui.request.ArticleFetchRequest;
import cho.o.me.blog.article.ui.response.ArticleResponse;
import cho.o.me.blog.exception.ArticleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public Article findBySlug(String slug) {
        return articleRepository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug + "is not exist"));
    }

    public List<Article> articles(Pageable pageable, ArticleFetchRequest request) {
        return articleRepository.findAll(request.getAuthor(), request.getTag(), request.getFavorited(), pageable);
    }
}