package cho.o.me.blog.account.domain;

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
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    String password;
    String token;

    public Account(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }


    public void update(UpdateRequest updateRequest) {
        this.email = updateRequest.getEmail();
        this.password = updateRequest.getPassword();
    }
}
