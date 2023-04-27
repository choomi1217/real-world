package cho.o.me.blog.account.ui;

import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.request.LoginRequest;
import cho.o.me.blog.account.ui.response.AccountResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("회원가입")
    void register() throws Exception{
        AccountRequest request = new AccountRequest("james@gmail.com", "james", "password");
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        String email = "john@john.com";
        String password = "john";
        String username = "JohnPark";

        registerAccount(new AccountRequest(email, password, username), mapper);

        LoginRequest request = new LoginRequest(email, password);
        String content = mapper.writeValueAsString(request);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email").value(email))
                .andExpect(jsonPath("$.user.token").isString())
                .andExpect(jsonPath("$.user.username").value(password));
    }

    private AccountResponse registerAccount(AccountRequest accountRequest, ObjectMapper mapper) throws Exception {

        String responseAsString = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountRequest))
        ).andReturn().getResponse().getContentAsString();

        return mapper.readValue(responseAsString, AccountResponse.class);
    }

}