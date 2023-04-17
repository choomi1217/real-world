package cho.o.me.blog.follow.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String followedName;
    private String followerName;

    public Follow(String followedName, String followerName) {
        this.followedName = followedName;
        this.followerName = followerName;
    }

}
