package cho.o.me.blog.follow.repository;

import cho.o.me.blog.follow.domain.Follow;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Long>{

    boolean existsFollowByFollowerNameAndFollowedName(String followerName, String followedName);

}
