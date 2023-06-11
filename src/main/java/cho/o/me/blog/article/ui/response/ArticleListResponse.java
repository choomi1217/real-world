package cho.o.me.blog.article.ui.response;

import java.util.List;

public class ArticleListResponse {
    List<ArticleResponse> articles;
    Integer articlesCount;

    public ArticleListResponse(List<ArticleResponse> articles, Integer articlesCount) {
        this.articles = articles;
        this.articlesCount = articlesCount;
    }
}
