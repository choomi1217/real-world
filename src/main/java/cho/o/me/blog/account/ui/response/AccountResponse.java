package cho.o.me.blog.account.ui.response;

import cho.o.me.blog.account.domain.Account;
import cho.o.me.blog.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record AccountResponse(String username, String email, String token, String bio, String image) {

    public static AccountResponse from(Account account, Member member) {
        return new AccountResponse(
                member.getUsername(),
                account.getEmail(),
                account.getToken(),
                member.getBio(),
                member.getImage()
        );
    }
}
