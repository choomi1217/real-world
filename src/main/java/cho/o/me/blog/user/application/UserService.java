package cho.o.me.blog.user.application;

import cho.o.me.blog.jwt.JwtService;
import cho.o.me.blog.user.domain.User;
import cho.o.me.blog.user.repository.UserRepository;
import cho.o.me.blog.user.ui.reauest.LoginRequest;
import cho.o.me.blog.user.ui.reauest.UserRequest;
import cho.o.me.blog.user.ui.response.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    @Transactional
    public cho.o.me.blog.user.ui.response.UserResponse register(UserRequest userRequest) {
        User user = userRepository.save(new User(userRequest.getUsername(), userRequest.getEmail(), userRequest.getPassword()));

        return cho.o.me.blog.user.ui.response.UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .token(user.getEmail())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }

    public UserResponse user(String email) {
        User user = userRepository.findByEmail(email);
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .token(jwtService.token(user.getEmail()))
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }


    public UserResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user.getPassword() != loginRequest.getPassword()){
            throw new IllegalArgumentException("password is not matched");
        }
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .token(jwtService.token(user.getEmail()))
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }
}
