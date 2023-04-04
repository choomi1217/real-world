package cho.o.me.blog.user.ui.reauest;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
