package cho.o.me.blog.favorite.ui;

import cho.o.me.blog.account.AccountStep;
import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.article.ArticleStep;
import cho.o.me.blog.article.ui.request.CreateArticleRequest;
import cho.o.me.blog.article.ui.response.ArticleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FavoriteControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    AccountStep accountStep;
    ArticleStep articleStep;

    @BeforeEach
    public void setup() throws Exception {
       accountStep = new AccountStep(mockMvc);
       articleStep = new ArticleStep(mockMvc);
    }

    @DisplayName("제리는 톰의 게시글에 좋아요를 누를 수 있다")
    @Test
    public void favorite() throws Exception {
        //given
        AccountResponse tom = accountStep.signUpAndLogin("tom@gmail.com", "tom", "tom");
        AccountResponse jerry = accountStep.signUpAndLogin("jerry@gmail.com", "jerry", "jerry");
        CreateArticleRequest articleRequest = CreateArticleRequest.builder().title("tom title").body("tom body").description("tom description").tagNameList(List.of("tag1","tag2")).build();
        ArticleResponse testArticle = articleStep.createTestArticle(articleRequest, tom);

        //when&then
        String slug = testArticle.getSlug();

        mockMvc.perform(get("/api/articles/"+slug+"/favorite")
                        .header(HttpHeaders.AUTHORIZATION, "Token " + jerry.token()))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("favorited").value("true"))
                //.andExpect(jsonPath("favoritesCount").value("1"))
                .andDo(print());
    }


}