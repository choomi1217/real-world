package cho.o.me.blog.article.ui.response;

import cho.o.me.blog.article.domain.Article;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@JsonRootName("article")
public class ArticleResponse {

    String title;
    String slug;
    String description;
    String body;
    List<String> tagList;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.slug = article.getSlug();
        this.description = article.getDescription();
        this.body = article.getBody();
        this.tagList = getTagNames(article.getTagList());
    }

    private List<String> getTagNames(List<UUID> tagList) {;
        return null;
    }
}
