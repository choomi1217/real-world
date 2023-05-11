package cho.o.me.blog.article.ui;

import cho.o.me.blog.account.AccountStep;
import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.article.ui.request.ArticleRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        List<String> tagList = List.of("tag1","tag2");

        ArticleRequest articleRequest = ArticleRequest.builder()
                .title(title)
                .description(description)
                .body(body)
                .tagList(tagList)
                .build();

        String articleContent = mapper.writeValueAsString(articleRequest);

        mockMvc.perform(post("/api/articles")
                        .header(HttpHeaders.AUTHORIZATION, "Token " + author.token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleContent))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.artice.title").value(title))
//                .andExpect(jsonPath("$.artice.description").value(description))
//                .andExpect(jsonPath("$.artice.body").value(body))
//                .andExpect(jsonPath("$.artice.tagList[0]").value(tagList.get(0)))
//                .andExpect(jsonPath("$.artice.tagList[1]").value(tagList.get(1)))
//                .andExpect(jsonPath("$.artice.author.username").value(author.username()))
                .andDo(print());
    }

    //Get Article
    @DisplayName("게시글 한 건을 조회할 수 있다.")
    @Test
    public void articels(){

    }

    //Feed Articles
    //List Articles

    //Update Article
    //Delete Article
    //Favorite Article
    //Unfavorite Article

}