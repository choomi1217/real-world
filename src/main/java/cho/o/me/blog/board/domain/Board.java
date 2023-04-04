package cho.o.me.blog.board.domain;


import cho.o.me.blog.tag.domain.Tag;
import cho.o.me.blog.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Board")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    private Long id;
    private String title;
    private String content;
    private Date lastUpdate;

    @ManyToOne
    private User author;

    @ManyToMany
    private Set<Category> category;

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();
}
