package cho.o.me.blog.account.domain;

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
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    String password;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public void update(UpdateRequest updateRequest) {
        this.email = updateRequest.getEmail();
        this.password = updateRequest.getPassword();
    }
}
