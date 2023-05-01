package cho.o.me.blog.account.ui.request;

import cho.o.me.blog.account.domain.Account;
import org.springframework.security.crypto.password.PasswordEncoder;

public record UpdateRequest(String email, String password, String username, String image, String bio){
    public UpdateRequest toAccountWithEncodedPassword(UpdateRequest request, PasswordEncoder passwordEncoder) {
        String encodedPassword = getEncodedPassword(passwordEncoder);
        return new UpdateRequest(request.email, encodedPassword, request.username, request.image, request.bio);
    }
    public String getEncodedPassword(PasswordEncoder passwordEncoder){
        return passwordEncoder.encode(password);
    }
}
