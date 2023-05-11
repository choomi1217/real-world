package cho.o.me.blog.article.ui.response;

import cho.o.me.blog.article.domain.Article;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;
import java.util.UUID;

@JsonRootName("article")
public class ArticleResponse {

    String title;
    String description;
    String body;
    List<String> tagList;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.description = article.getDescription();
        this.body = article.getBody();
        this.tagList = getTagNames(article.getTagList());
    }

    private List<String> getTagNames(List<UUID> tagList) {
        //tagList.stream().map()
        return null;
    }
}
