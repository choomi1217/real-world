package cho.o.me.blog.member.application;

import cho.o.me.blog.follow.application.FollowService;
import cho.o.me.blog.member.domain.Member;
import cho.o.me.blog.member.ui.response.ProfileResponse;
import cho.o.me.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public ProfileResponse profile(String currentUsername, String username) {
        return null;
    }

    public ProfileResponse profile(String username) {
        return new ProfileResponse( memberRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found")));
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
