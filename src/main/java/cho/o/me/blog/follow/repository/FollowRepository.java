package cho.o.me.blog.follow.repository;

import cho.o.me.blog.follow.domain.Follow;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Long>{

    @Query("select count(*) > 0 from Follow f where f.followerName = :currentUsername and f.followedName = :username")
    boolean exists(@Param("currentUsername") String currentUsername, @Param("username") String username);

}
