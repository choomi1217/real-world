package cho.o.me.blog.article.ui;

import cho.o.me.blog.account.AccountStep;
import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.article.ArticleStep;
import cho.o.me.blog.article.ui.request.ArticleCreateRequest;
import cho.o.me.blog.article.ui.response.ArticleCreateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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

import java.util.ArrayList;
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
        author = accountStep.signUp("tom@gmail.com", "tom", "tom");
    }

    @DisplayName("게시글 한 건을 등록할 수 있다.")
    @Test
    public void create() throws Exception {

        String title = "title";
        String description = "description";
        String body = "body";
        List<String> tagList = List.of("tag1", "tag2");

        ArticleCreateRequest createArticleRequest = ArticleCreateRequest.builder()
                .title(title)
                .description(description)
                .body(body)
                .tagNameList(tagList)
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
        String title = "How to train your dragon";
        String description = "Ever wonder how?";
        String body = "It takes a Jacobian";
        List<String> tagList = List.of("dragons", "training");
        ArticleCreateRequest createArticleRequest = ArticleCreateRequest.builder()
                .title(title)
                .description(description)
                .body(body)
                .tagNameList(tagList)
                .build();
        ArticleCreateResponse article = createArticle(createArticleRequest);
        //when, then
        mockMvc.perform(get("/api/articles/"+article.getSlug()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(title))
                .andExpect(jsonPath("description").value(description))
                .andExpect(jsonPath("body").value(body))
                .andDo(print());
    }

    private ArticleCreateResponse createArticle(ArticleCreateRequest createArticleRequest) throws Exception {
        String articleContent = mapper.writeValueAsString(createArticleRequest);

        String contentAsString = mockMvc.perform(post("/api/articles")
                .header(HttpHeaders.AUTHORIZATION, "Token " + author.token())
                .contentType(MediaType.APPLICATION_JSON)
                .content(articleContent)).andReturn().getResponse().getContentAsString();

        return mapper.readValue(contentAsString, ArticleCreateResponse.class);

    }

    //List Articles
    @DisplayName("게시글을 여러개 조회 할 수 있다.")
    @Test
    public void articles() throws Exception {
        List<ArticleCreateResponse> testArticles = getFakeArticles();
        mockMvc.perform(get("/api/articles")
                        .header(HttpHeaders.AUTHORIZATION, "Token " + author.token())
                        .param("limit", "10")
                        .param("offset", "0"))
                .andDo(print());
    }

    private List<ArticleCreateResponse> getFakeArticles() throws Exception {
        ArticleStep articleStep = new ArticleStep(mockMvc);
        List<ArticleCreateResponse> testArticles = new ArrayList<ArticleCreateResponse>();

        for(int i=0; i<30; i++){
            ArticleCreateRequest request = getCreateArticleRequest();
            ArticleCreateResponse testArticle = articleStep.createTestArticle(request, author);
            testArticles.add(testArticle);
        }
        return testArticles;
    }

    private static ArticleCreateRequest getCreateArticleRequest() {
        Faker faker = new Faker();
        String title = faker.book().title();
        String description = faker.lorem().sentence(3);
        String body = faker.lorem().sentence(20);
        List<String> tagNameList = faker.lorem().words(2);
        return new ArticleCreateRequest(title, description, body, tagNameList);
    }


    //Feed Articles

    //Update Article
    //Delete Article
    //Favorite Article
    //Unfavorite Article

}