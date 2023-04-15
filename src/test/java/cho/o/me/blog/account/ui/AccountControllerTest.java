package cho.o.me.blog.account.ui;

import cho.o.me.blog.account.ui.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void login() throws Exception {
        String email = "test";
        String password = "test";
        LoginRequest request = new LoginRequest(email, password);
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/users/login")
                        .content(content)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email").value(email))
                .andExpect(jsonPath("$.user.token").isString())
                .andExpect(jsonPath("$.user.username").value(password));
    }

}