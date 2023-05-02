package cho.o.me.blog.account;

import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.response.AccountResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AccountStep {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    private final String TEST_EMAIL = "john@john.com", TEST_USERNAME = "john", TEST_PASSWORD = "JohnPark";

    public AccountStep(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public AccountRequest signUp(){
        return new AccountRequest(TEST_EMAIL, TEST_USERNAME, TEST_PASSWORD);
    }

    public AccountRequest signUp(String email, String username, String password){
        return new AccountRequest(email, username, password);
    }

    public AccountResponse login() throws Exception {

        AccountRequest accountRequest = signUp();

        String responseAsString = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountRequest))
        ).andReturn().getResponse().getContentAsString();

        return mapper.readValue(responseAsString, AccountResponse.class);
    }

    public AccountResponse login(AccountRequest accountRequest) throws Exception {

        String responseAsString = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountRequest))
        ).andReturn().getResponse().getContentAsString();

        return mapper.readValue(responseAsString, AccountResponse.class);
    }

    public AccountResponse signUpAndLogin(String email, String username, String password) throws Exception {
        return login(signUp(email,username,password));
    }
}
