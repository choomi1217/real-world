package cho.o.me.blog.user.application;

import cho.o.me.blog.jwt.JwtService;
import cho.o.me.blog.user.domain.User;
import cho.o.me.blog.user.repository.UserRepository;
import cho.o.me.blog.user.ui.reauest.LoginRequest;
import cho.o.me.blog.user.ui.reauest.UpdateRequest;
import cho.o.me.blog.user.ui.reauest.UserRequest;
import cho.o.me.blog.user.ui.response.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse register(UserRequest userRequest) {
        User user = userRepository.save(
                new User( userRequest.getUsername()
                        , userRequest.getEmail()
                        , passwordEncoder.encode(userRequest.getPassword())
                ));

        return userToResponse(user);
    }

    public UserResponse user(String email) {
        return userToResponse(userRepository.findByEmail(email));
    }


    public UserResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail());

        if(passwordEncoder.matches(user.getPassword(), loginRequest.getPassword())){
            throw new IllegalArgumentException("password is not matched");
        }

        return userToResponse(user);
    }

    public UserResponse update(String userEmail, UpdateRequest updateRequest) {
        return userToResponse(userRepository.updateByEmail(userEmail, updateRequest));
    }

    private UserResponse userToResponse(User user){
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .token(jwtService.token(user.getEmail()))
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }

}
