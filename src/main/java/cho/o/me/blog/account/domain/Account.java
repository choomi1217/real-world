package cho.o.me.blog.account.domain;

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
}
