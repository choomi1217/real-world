package cho.o.me.blog.member.application;

import cho.o.me.blog.follow.application.FollowService;
import cho.o.me.blog.member.domain.Member;
import cho.o.me.blog.member.repository.MemberRepository;
import cho.o.me.blog.member.ui.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberProfileService {
    private final MemberService memberService;
    private final FollowService followService;


    public ProfileResponse profile(String followerEmail , String followedName) {
        Member followerMember = memberService.findByEmail(followerEmail).orElseThrow(() -> new UsernameNotFoundException(followerEmail + "is not exist"));
        Member followedMember = memberService.findByUsername(followedName).orElseThrow(() -> new UsernameNotFoundException(followedName + "is not exist"));
        boolean exists = followService.exists(followerMember.getUsername(), followedMember.getUsername());
        return new ProfileResponse(followedMember, exists);
    }

    public ProfileResponse profile(String username) {
        Member member = memberService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " is not exist"));
        return new ProfileResponse(member);
    }
}
