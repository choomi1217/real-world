package cho.o.me.blog.tag.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@Entity
@Table(name = "tag")
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    private UUID id;

    @Column(unique = true)
    private String name;

    public Tag(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
