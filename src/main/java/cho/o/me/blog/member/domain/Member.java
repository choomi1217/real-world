package cho.o.me.blog.member.domain;

import cho.o.me.blog.account.ui.request.UpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String bio;
    private String image;

    public void update(UpdateRequest updateRequest) {
        this.email = updateRequest.email();
        this.bio = updateRequest.bio();
        this.image = updateRequest.image();
        this.username = updateRequest.username();
    }
}
