package cho.o.me.blog.article.ui.request;

import lombok.Getter;

@Getter
public class ArticleRequest {

    String tag;
    String author;
    String favorite;
    Integer limit;
    Integer offset;

}
