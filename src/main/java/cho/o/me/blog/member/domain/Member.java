package cho.o.me.blog.member.domain;

import cho.o.me.blog.account.ui.request.UpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String bio;
    private String image;

    public void update(UpdateRequest updateRequest) {
        this.email = updateRequest.getEmail();
        this.bio = updateRequest.getBio();
        this.image = updateRequest.getImage();
        this.username = updateRequest.getUsername();
    }
}
