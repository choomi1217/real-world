package cho.o.me.blog.account.ui.request;

import cho.o.me.blog.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

public record AccountRequest(String email, String username, String password){

}
