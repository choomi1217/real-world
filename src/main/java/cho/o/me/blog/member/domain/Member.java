package cho.o.me.blog.member.domain;

import cho.o.me.blog.account.ui.request.UpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
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
