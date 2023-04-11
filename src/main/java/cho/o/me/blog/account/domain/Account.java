package cho.o.me.blog.account.domain;

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
public class Account {
    @Id
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
