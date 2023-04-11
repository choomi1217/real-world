package cho.o.me.blog.account.ui.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountResponse {
    private String username;
    private String email;
    private String token;
    private String bio;
    private String image;
}
