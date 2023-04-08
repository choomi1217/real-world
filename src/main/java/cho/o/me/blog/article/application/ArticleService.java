package cho.o.me.blog.article.application;

import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.article.repository.ArticleRepository;
import cho.o.me.blog.article.repository.ArticleSpecifications;
import cho.o.me.blog.article.ui.request.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class ArticleService {

    ArticleRepository articleRepository;

    public Page<Article> articles(ArticleRequest request) {
        Integer offset = request.getOffset();
        Integer limit = request.getLimit();
        Pageable pageable = PageRequest.of(offset,limit);

        Specification<Article> spec = ArticleSpecifications.search(request);

        return articleRepository.findAll(spec, pageable);
    }
}
