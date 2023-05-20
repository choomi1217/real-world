package cho.o.me.blog.member.application;

import cho.o.me.blog.exception.NotFollowingException;
import cho.o.me.blog.follow.application.FollowService;
import cho.o.me.blog.member.domain.Member;
import cho.o.me.blog.member.ui.response.ProfileResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFollowService {

    private final FollowService followService;
    private final MemberService memberService;


    public ProfileResponse follow(String followerEmail, String followedName) {
        Member follower = memberService.findByEmail(followerEmail).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Member followed = memberService.findByUsername(followedName).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        followService.follow(follower.getUsername(), followed.getUsername());
        return new ProfileResponse(followed, true);
    }

    @Transactional
    public ProfileResponse unfollow(String followerEmail, String followedName) throws NotFollowingException {
        Member follower = memberService.findByEmail(followerEmail).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Member followed = memberService.findByUsername(followedName).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        if( !isItFollow(follower, followed) ){
            throw new NotFollowingException( String.format("%s is not following %s.", follower.getUsername(), followed.getUsername()));
        }

        followService.unfollow(follower.getUsername(), followed.getUsername());
        return new ProfileResponse(followed, false);
    }

    private boolean isItFollow(Member follower, Member followed) {
        return followService.exists(follower.getUsername(), followed.getUsername());
    }
}
