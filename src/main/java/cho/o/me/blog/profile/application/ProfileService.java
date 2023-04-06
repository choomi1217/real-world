package cho.o.me.blog.profile.application;

import cho.o.me.blog.follow.application.FollowService;
import cho.o.me.blog.profile.repository.ProfileRepository;
import cho.o.me.blog.profile.ui.response.ProfileResponse;
import cho.o.me.blog.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    UserService userServicel;
    FollowService followService;
    ProfileRepository profileRepository;


    public ProfileResponse profile(String currentUsername, String username) {
        return new ProfileResponse( userServicel.user(username), followService.exists(currentUsername, username));
    }

    public ProfileResponse profile(String username) {
        return new ProfileResponse( userServicel.user(username));
    }
}
