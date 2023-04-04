package cho.o.me.blog.tag.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tag")
public class Tag {
    @Id
    private Long id;
    private String name;
}
