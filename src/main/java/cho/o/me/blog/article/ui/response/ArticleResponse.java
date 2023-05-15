package cho.o.me.blog.article.ui.response;

import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.tag.domain.Tag;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@JsonRootName("article")
public class ArticleResponse {

    String title;
    String slug;
    String description;
    String body;
    List<String> tagList;

    public ArticleResponse(String title, String slug, String description, String body, List<Tag> tagList) {
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.body = body;
        this.tagList = tagList.stream().map(Tag::getName).collect(Collectors.toList());
    }
}
