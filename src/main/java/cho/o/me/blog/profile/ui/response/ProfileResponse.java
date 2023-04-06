package cho.o.me.blog.profile.ui.response;

import cho.o.me.blog.follow.domain.Follow;
import cho.o.me.blog.user.domain.User;
import cho.o.me.blog.user.ui.response.UserResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProfileResponse {

    User user;
    Boolean following;

    public ProfileResponse(UserResponse user, boolean exist) {
    }

    public ProfileResponse(UserResponse user) {
    }
}
