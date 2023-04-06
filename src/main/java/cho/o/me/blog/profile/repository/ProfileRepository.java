package cho.o.me.blog.profile.repository;

import cho.o.me.blog.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Follow, Long> {
}
