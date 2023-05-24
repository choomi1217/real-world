package cho.o.me.blog.article.ui.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleFetchRequest {
    private String tag;
    private String author;
    private String favorited;
}
