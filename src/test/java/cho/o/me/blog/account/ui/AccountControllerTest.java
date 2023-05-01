package cho.o.me.blog.account.ui;

import cho.o.me.blog.account.application.AccountUsercase;
import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.request.LoginRequest;
import cho.o.me.blog.account.ui.request.UpdateRequest;
import cho.o.me.blog.account.ui.response.AccountResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountUsercase accountUsercase;
    ObjectMapper mapper = new ObjectMapper();
    private final String TEST_EMAIL = "john@john.com", TEST_USERNAME = "john", TEST_PASSWORD = "JohnPark";

    @Test
    @DisplayName("회원가입 성공")
    void register() throws Exception {
        //given
        AccountRequest request = getTestAccount();
        //when&then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.email").value(TEST_EMAIL))
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.username").value(TEST_USERNAME))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공")
    void login() throws Exception {
        //given
        registerAccount(getTestAccount());
        //when
        LoginRequest request = new LoginRequest(TEST_EMAIL, TEST_PASSWORD);
        String content = mapper.writeValueAsString(request);
        //then
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(TEST_EMAIL))
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.username").value(TEST_USERNAME))
                .andDo(print());
    }

    @DisplayName("회원조회 성공")
    @Test
    public void user() throws Exception {
        // given 회원가입 & 로그인
        // 회원가입
        AccountResponse accountResponse = registerAccount(getTestAccount());
        // 로그인
        LoginRequest loginRequest = new LoginRequest(TEST_EMAIL, TEST_PASSWORD);

        String responseString = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest))
        ).andReturn().getResponse().getContentAsString();

        AccountResponse loginResponse = mapper.readValue(responseString, AccountResponse.class);

        //when & then
        mockMvc.perform(get("/api/user")
                    .header(HttpHeaders.AUTHORIZATION, "Token " + loginResponse.token())
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(TEST_USERNAME))
                .andExpect(jsonPath("$.email").value(TEST_EMAIL))
                .andDo(print());
    }

    @DisplayName("회원수정 성공")
    @Test
    public void update() throws Exception {
        // given 회원가입 & 로그인 & 수정정보
        // 회원가입
        AccountResponse accountResponse = registerAccount(getTestAccount());
        // 로그인
        LoginRequest loginRequest = new LoginRequest(TEST_EMAIL, TEST_PASSWORD);

        String responseString = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest))
        ).andReturn().getResponse().getContentAsString();

        AccountResponse loginResponse = mapper.readValue(responseString, AccountResponse.class);

        // 수정정보
        String updateEmail = "jonny@update.com";
        String updatePassword = "jonnyUpdated";
        String updateUsername = "jonnyUp";
        String updateImage = "jonny_update_image";
        String updateBio = "jonny_update_bio";
        UpdateRequest updateRequest = new UpdateRequest(updateEmail, updatePassword, updateUsername, updateImage, updateBio);
        String content = mapper.writeValueAsString(updateRequest);

        //then
        mockMvc.perform(put("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Token " + loginResponse.token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(updateEmail))
                .andExpect(jsonPath("$.username").value(updateUsername))
                .andExpect(jsonPath("$.image").value(updateImage))
                .andExpect(jsonPath("$.bio").value(updateBio))
                .andDo(print());
    }

    private AccountResponse registerAccount(AccountRequest accountRequest) throws Exception {

        String responseAsString = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountRequest))
        ).andReturn().getResponse().getContentAsString();

        return mapper.readValue(responseAsString, AccountResponse.class);
    }

    private AccountRequest getTestAccount() {
        return new AccountRequest(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD);
    }
}