package cho.o.me.blog.account.ui.response;

import cho.o.me.blog.account.domain.Account;
import cho.o.me.blog.member.domain.Member;
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

    public AccountResponse(Account account, Member member) {
                this.username = member.getUsername();
                this.email = account.getEmail();
                this.token = account.getToken();
                this.bio = member.getBio();
                this.image = member.getImage();
    }
}
