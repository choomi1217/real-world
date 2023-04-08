package cho.o.me.blog.article.application;

import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.article.ui.request.ArticleRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ArticleService {
    public List<Article> articles(ArticleRequest request) {

        Integer offset = request.getOffset();
        Integer limit = request.getLimit();

        Pageable pageable = PageRequest.of(offset,limit);

        return null;
    }
}
