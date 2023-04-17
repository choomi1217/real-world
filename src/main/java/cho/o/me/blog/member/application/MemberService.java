package cho.o.me.blog.member.application;

import cho.o.me.blog.follow.application.FollowService;
import cho.o.me.blog.member.domain.Member;
import cho.o.me.blog.member.ui.response.ProfileResponse;
import cho.o.me.blog.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    FollowService followService;
    MemberRepository memberRepository;

    public ProfileResponse profile(String currentUsername, String username) {
        return new ProfileResponse( memberRepository.findByEmail(username), followService.exists(currentUsername, username));
    }

    public ProfileResponse profile(String username) {
        return new ProfileResponse( memberRepository.findByEmail(username));
    }

    public Member save(Member build) {
    }

    public Member findByEmail(String email) {
    }
}
