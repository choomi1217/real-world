package cho.o.me.blog.account.ui.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
