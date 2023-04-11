package cho.o.me.blog.member.repository;

import cho.o.me.blog.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    Member update(String email, Member byEmail);
}
