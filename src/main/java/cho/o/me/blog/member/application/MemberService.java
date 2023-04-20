package cho.o.me.blog.member.application;

import cho.o.me.blog.follow.application.FollowService;
import cho.o.me.blog.member.domain.Member;
import cho.o.me.blog.member.ui.response.ProfileResponse;
import cho.o.me.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final FollowService followService;
    private final MemberRepository memberRepository;

    public ProfileResponse profile(String currentUsername, String username) {
        return new ProfileResponse( memberRepository.findByEmail(username), followService.exists(currentUsername, username));
    }

    public ProfileResponse profile(String username) {
        return new ProfileResponse( memberRepository.findByEmail(username));
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
