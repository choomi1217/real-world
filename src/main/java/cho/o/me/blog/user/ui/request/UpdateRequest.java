package cho.o.me.blog.user.ui.request;

import lombok.Getter;

@Getter
public class UpdateRequest {
    private String email;
    private String image;
    private String bio;
}
