package cho.o.me.blog.account.ui;

import cho.o.me.blog.account.application.AccountUsercase;
import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.request.LoginRequest;
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
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountUsercase accountUsercase;

    ObjectMapper mapper = new ObjectMapper();

    private final String TEST_EMAIL = "john@john.com";
    private final String TEST_USERNAME = "john";
    private final String TEST_PASSWORD = "JohnPark";

    @Test
    @DisplayName("회원가입")
    @Transactional
    void register() throws Exception {
        AccountRequest request = new AccountRequest(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD);
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
    @DisplayName("로그인")
    @Transactional
    void login() throws Exception {
        registerAccount(new AccountRequest(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD));
        LoginRequest request = new LoginRequest(TEST_EMAIL, TEST_PASSWORD);
        String content = mapper.writeValueAsString(request);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(TEST_EMAIL))
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.username").value(TEST_USERNAME));
    }

    @DisplayName("회원조회")
    @Test
    public void user() throws Exception {
        AccountResponse accountResponse = registerAccount(new AccountRequest(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD));
//        LoginRequest loginRequest = new LoginRequest(accountResponse.email(), TEST_PASSWORD);
//        mockMvc.perform(post("/api/users/login")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(mapper.writeValueAsString(loginRequest))
//                ).andReturn().getResponse();

        mockMvc.perform(get("/api/user")
                    .header(HttpHeaders.AUTHORIZATION, "Token " + accountResponse.token())
                ).andExpect(status().isOk());
    }

    @DisplayName("회원수정")
    @Test
    public void update() throws Exception {
        mockMvc.perform(put("")
                        .header("", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk());
    }

    private AccountResponse registerAccount(AccountRequest accountRequest) throws Exception {
        String responseAsString = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountRequest))
        ).andReturn().getResponse().getContentAsString();

        return mapper.readValue(responseAsString, AccountResponse.class);
    }

}