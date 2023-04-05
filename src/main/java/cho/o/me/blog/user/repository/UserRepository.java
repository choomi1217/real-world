package cho.o.me.blog.user.repository;

import cho.o.me.blog.user.domain.User;
import cho.o.me.blog.user.ui.reauest.UpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User save (User user);

    User findByEmail(String email);

    User updateByEmail(String email, UpdateRequest updateRequest);

}
