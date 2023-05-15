package cho.o.me.blog.tag.repository;

import cho.o.me.blog.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findAllByNameIn(List<String> names);

    List<Tag> findAllByIdIn(List<UUID> tags);
}
