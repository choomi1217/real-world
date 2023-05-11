package cho.o.me.blog.article.ui.request;

import cho.o.me.blog.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {

    String title;
    String description;
    String body;
    List<String> tagList;

}
