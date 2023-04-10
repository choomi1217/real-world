package cho.o.me.blog.follow.application;

import cho.o.me.blog.follow.domain.Follow;
import cho.o.me.blog.follow.repository.FollowRepository;
import cho.o.me.blog.profile.ui.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    FollowRepository followRepository;
    MemberService memberService;

    public boolean exists(String currentUsername, String username) {
        return followRepository.exists(currentUsername, username);
    }

    public ProfileResponse follow(String currentUsername, String username) {
        followRepository.save(new Follow(currentUsername, username));
        UserResponse user = memberService.user(username);
        return new ProfileResponse(user, true);
    }

    public ProfileResponse unfollow(String currentUsername, String username) {
        followRepository.delete(new Follow(currentUsername, username));
        UserResponse user = memberService.user(username);
        return new ProfileResponse(user, false);
    }
}
