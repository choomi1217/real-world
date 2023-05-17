package cho.o.me.blog.article.ui;

import cho.o.me.blog.account.AccountStep;
import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.article.ui.request.CreateArticleRequest;
import cho.o.me.blog.article.ui.response.CreateArticleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    AccountResponse author;

    @BeforeEach
    public void setup() throws Exception {
        AccountStep accountStep = new AccountStep(mockMvc);
        author = accountStep.signUpAndLogin("tom@gmail.com", "tom", "tom");
    }

    @DisplayName("게시글 한 건을 등록할 수 있다.")
    @Test
    public void create() throws Exception {

        String title = "title";
        String description = "description";
        String body = "body";
        List<String> tagList = List.of("tag1", "tag2");

        CreateArticleRequest createArticleRequest = CreateArticleRequest.builder()
                .title(title)
                .description(description)
                .body(body)
                .tagList(tagList)
                .build();

        String articleContent = mapper.writeValueAsString(createArticleRequest);

        mockMvc.perform(post("/api/articles")
                        .header(HttpHeaders.AUTHORIZATION, "Token " + author.token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleContent))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("title").value(title))
                .andExpect(jsonPath("description").value(description))
                .andExpect(jsonPath("body").value(body))
                .andExpect(jsonPath("tagList", containsInAnyOrder(tagList.toArray(new String[0]))))
                .andDo(print());
    }

    @DisplayName("게시글 한 건을 조회할 수 있다.")
    @Test
    public void articels() throws Exception {
        //given
        CreateArticleResponse article = createArticle();
        //when, then
        mockMvc.perform(get("/api/articles/"+article.getSlug()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private CreateArticleResponse createArticle() throws Exception {
        String title = "How to train your dragon";
        String description = "Ever wonder how?";
        String body = "It takes a Jacobian";
        List<String> tagList = List.of("dragons", "training");

        CreateArticleRequest createArticleRequest = CreateArticleRequest.builder()
                .title(title)
                .description(description)
                .body(body)
                .tagList(tagList)
                .build();

        String articleContent = mapper.writeValueAsString(createArticleRequest);

        String contentAsString = mockMvc.perform(post("/api/articles")
                .header(HttpHeaders.AUTHORIZATION, "Token " + author.token())
                .contentType(MediaType.APPLICATION_JSON)
                .content(articleContent)).andReturn().getResponse().getContentAsString();

        return mapper.readValue(contentAsString, CreateArticleResponse.class);

    }

    //Feed Articles
    //List Articles

    //Update Article
    //Delete Article
    //Favorite Article
    //Unfavorite Article

}