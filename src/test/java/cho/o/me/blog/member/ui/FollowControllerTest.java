package cho.o.me.blog.member.ui;

import cho.o.me.blog.account.AccountStep;
import cho.o.me.blog.account.ui.response.AccountResponse;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FollowControllerTest {

    @Autowired
    MockMvc mockMvc;
    AccountStep step;
    @BeforeEach
    public void setup() throws Exception {
        step = new AccountStep(mockMvc);
    }

    @Test
    @DisplayName("톰은 제리를 팔로우 할 수 있다.")
    public void follow() throws Exception {

        //given
        AccountResponse tom = step.signUpAndLogin("tom@gmail.com", "tom", "tom");
        AccountResponse jerry = step.signUpAndLogin("jerry@gmail.com", "jerry", "jerry");

        //when&then
        mockMvc.perform(post("/api/profiles/{username}/follow", jerry.username())
                    .header(HttpHeaders.AUTHORIZATION, "Token " + tom.token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.following").value("true"))
                .andExpect(jsonPath("$.username").value(jerry.username()))
                .andDo(print());
    }

    @Test
    @DisplayName("톰은 제리를 언팔로우 할 수 있다.")
    public void unfollow() throws Exception {
        //given
        AccountResponse tom = step.signUpAndLogin("tom@gmail.com", "tom", "tom");
        AccountResponse jerry = step.signUpAndLogin("jerry@gmail.com", "jerry", "jerry");

        mockMvc.perform(post("/api/profiles/{username}/follow", jerry.username())
                .header(HttpHeaders.AUTHORIZATION, "Token " + tom.token()));
        //when & then
        mockMvc.perform(delete("/api/profiles/{username}/follow", jerry.username())
                        .header(HttpHeaders.AUTHORIZATION, "Token " + tom.token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.following").value("false"))
                .andExpect(jsonPath("$.username").value(jerry.username()))
                .andDo(print());
    }

}