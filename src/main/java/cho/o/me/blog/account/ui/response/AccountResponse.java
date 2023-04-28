package cho.o.me.blog.account.ui.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String username;
    private String email;
    private String token;
    private String bio;
    private String image;
}
