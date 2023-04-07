package cho.o.me.blog.tag.repository;

import cho.o.me.blog.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
