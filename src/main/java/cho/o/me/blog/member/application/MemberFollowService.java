package cho.o.me.blog.member.application;

import cho.o.me.blog.follow.application.FollowService;
import cho.o.me.blog.member.domain.Member;
import cho.o.me.blog.member.ui.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFollowService {

    private final FollowService followService;
    private final MemberService memberService;


    public ResponseEntity<ProfileResponse> follow(String followerEmail, String followedName) {
        Member follower = memberService.findByEmail(followerEmail).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Member followed = memberService.findByEmail(followedName).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        followService.follow(follower.getUsername(), followed.getUsername());
        return null;
    }

    public ResponseEntity<ProfileResponse> unfollow(String follower, String followed) {
        return null;
    }
}
