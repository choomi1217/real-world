package cho.o.me.blog.follow.application;

import cho.o.me.blog.follow.domain.Follow;
import cho.o.me.blog.follow.repository.FollowRepository;
import cho.o.me.blog.member.application.MemberService;
import cho.o.me.blog.member.ui.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public boolean exists(String followerName, String followedName) {
        return followRepository.existsFollowByFollowerNameAndFollowedName(followerName, followedName);
    }

    public void follow(String followerName, String followedName) {
        followRepository.save(new Follow(followerName, followedName));
    }

    public void unfollow(String followerName, String followedName) {
        followRepository.delete(new Follow(followerName, followedName));
    }
}
