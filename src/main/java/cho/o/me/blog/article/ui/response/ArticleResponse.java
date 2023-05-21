package cho.o.me.blog.article.ui.response;

import cho.o.me.blog.member.ui.response.ProfileResponse;
import cho.o.me.blog.tag.domain.Tag;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {

    String slug;
    String title;
    String description;
    String body;
    @JsonProperty("tagList")
    List<String> tagNameList;
    Date createdAt;
    Date updatedAt;
    Boolean favorited;
    Integer favoritesCount;
    ProfileResponse profile;

    public static ArticleResponse of(
            String slug,
            String title,
            String description,
            String body,
            List<Tag> allById,
            Date createdAt,
            Date updatedAt,
            Boolean favorited,
            int favoritesCount,
            String author) {
        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.slug = slug;
        articleResponse.title = title;
        articleResponse.description = description;
        articleResponse.body = body;
        articleResponse.tagNameList = allById.stream().map(Tag::getName).collect(Collectors.toList());
        articleResponse.createdAt = createdAt;
        articleResponse.updatedAt = updatedAt;
        articleResponse.favorited = favorited;
        articleResponse.favoritesCount = favoritesCount;
        return articleResponse;
    }
}
