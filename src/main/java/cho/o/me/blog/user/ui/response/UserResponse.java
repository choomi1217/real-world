package cho.o.me.blog.user.ui.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String username;
    private String email;
    private String token;
    private String bio;
    private String image;
}
