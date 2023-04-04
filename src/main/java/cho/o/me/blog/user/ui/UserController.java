package cho.o.me.blog.user.ui;

import cho.o.me.blog.user.ui.reauest.LoginRequest;
import cho.o.me.blog.user.application.UserService;
import cho.o.me.blog.user.ui.reauest.UserRequest;
import cho.o.me.blog.user.ui.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/api/users/login")
    public ResponseEntity<UserResponse> login(@Validated @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping(path = "/api/user")
    public ResponseEntity<UserResponse> user(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.user(userDetails.getUsername()));
    }

    @PostMapping(path = "/api/users")
    public ResponseEntity<UserResponse> register(@Validated @RequestBody UserRequest userRequest) throws URISyntaxException {
        return ResponseEntity.created(new URI("/api/user"))
                .body(userService.register(userRequest));
    }

}
