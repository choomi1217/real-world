package cho.o.me.blog.account.ui.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AccountRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
