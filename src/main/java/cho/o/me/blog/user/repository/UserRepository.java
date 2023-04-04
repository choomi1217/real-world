package cho.o.me.blog.user.repository;

import cho.o.me.blog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User save (User user);

    User findByEmail(String email);
}
