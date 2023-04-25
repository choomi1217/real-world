package cho.o.me.blog.account;

import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.response.AccountResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountStep {

    @Autowired
    MockMvc mockMvc;

    public AccountResponse registUser(AccountRequest request, ObjectMapper mapper) throws Exception {
        String requestAsString = mapper.writeValueAsString(request);

        String responseAsString = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsString)
        ).andReturn().getResponse().getContentAsString();

        return mapper.readValue(responseAsString, AccountResponse.class);
    }

}
