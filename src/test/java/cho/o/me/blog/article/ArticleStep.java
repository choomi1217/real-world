package cho.o.me.blog.article;

import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.article.ui.request.ArticleCreateRequest;
import cho.o.me.blog.article.ui.response.ArticleResponse;
import cho.o.me.blog.article.ui.response.ArticleCreateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ArticleStep {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public ArticleStep(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ArticleCreateResponse createTestArticle(ArticleCreateRequest request, AccountResponse author) throws Exception {
        String articleRequest = mapper.writeValueAsString(request);

        String createArticleResponseContent = mockMvc.perform(post("/api/articles")
                        .header(HttpHeaders.AUTHORIZATION, "Token " + author.token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleRequest))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return mapper.readValue(createArticleResponseContent, ArticleCreateResponse.class);
    }

    public ArticleResponse getTestArticle(ArticleCreateResponse article) throws Exception {
        String articleResponse = mockMvc.perform(get("/api/articles/" + article.getSlug()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return mapper.readValue(articleResponse, ArticleResponse.class);
    }
}
