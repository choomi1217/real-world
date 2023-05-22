package cho.o.me.blog.article.ui.response;

import cho.o.me.blog.tag.domain.Tag;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonRootName("article")
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateResponse {

    String title;
    String slug;
    String description;
    String body;
    List<String> tagList;

    public static ArticleCreateResponse of(String title, String slug, String description, String body, List<Tag> tagList) {
        ArticleCreateResponse articleResponse = new ArticleCreateResponse();
        articleResponse.title = title;
        articleResponse.slug = slug;
        articleResponse.description = description;
        articleResponse.body = body;
        articleResponse.tagList = tagList.stream().map(Tag::getName).collect(Collectors.toList());
        return articleResponse;
    }
}
