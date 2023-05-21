package cho.o.me.blog.account;

import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.response.AccountResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AccountStep {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public AccountStep(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public AccountRequest getAccountRequest(String email, String username, String password){
        return new AccountRequest(email, username, password);
    }

    public AccountResponse signUp(AccountRequest accountRequest) throws Exception {

        String responseAsString = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountRequest))
        ).andReturn().getResponse().getContentAsString();

        return mapper.readValue(responseAsString, AccountResponse.class);
    }

    public AccountResponse signUp(String email, String username, String password) throws Exception {
        return signUp(getAccountRequest(email,username,password));
    }

}
