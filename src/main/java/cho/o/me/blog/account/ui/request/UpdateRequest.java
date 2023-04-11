package cho.o.me.blog.account.ui.request;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class UpdateRequest {
    private String email;
    private String password;
    private String username;
    private String image;
    private String bio;

    public UpdateRequest encoding(PasswordEncoder passwordEncoder) {
         this.password = passwordEncoder.encode(password);
        return this;
    }
}
