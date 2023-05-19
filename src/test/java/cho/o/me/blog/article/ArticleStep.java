package cho.o.me.blog.article;

import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.article.ui.request.CreateArticleRequest;
import cho.o.me.blog.article.ui.response.ArticleResponse;
import cho.o.me.blog.article.ui.response.CreateArticleResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ArticleStep {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public ArticleStep(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ArticleResponse createTestArticle(CreateArticleRequest request, AccountResponse author) throws Exception {
        String articleContent = mapper.writeValueAsString(request);

        String responseAsString = mockMvc.perform(post("/api/articles")
                        .header(HttpHeaders.AUTHORIZATION, "Token " + author.token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleContent))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return mapper.readValue(responseAsString, ArticleResponse.class);
    }

}
