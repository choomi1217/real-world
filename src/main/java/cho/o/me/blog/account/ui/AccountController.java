package cho.o.me.blog.account.ui;

import cho.o.me.blog.account.application.AccountUsercase;
import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.request.LoginRequest;
import cho.o.me.blog.account.ui.request.UpdateRequest;
import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.exception.UserNotFoundElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountUsercase accountUsercase;
    @PostMapping("/api/users/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(accountUsercase.login(loginRequest));
        } catch (UserNotFoundElementException e) {
            return ResponseEntity.status(e.getCode().getHttpStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> user(@AuthenticationPrincipal UserDetails userDetails){
        try {
            return ResponseEntity.ok(accountUsercase.user(userDetails.getUsername()));
        } catch (UserNotFoundElementException e) {
            return ResponseEntity.status(e.getCode().getHttpStatus()).body(e.getMessage());
        }

    }

    @PostMapping("/api/users")
    public ResponseEntity<AccountResponse> register(@Validated @RequestBody AccountRequest request) throws URISyntaxException {
        return ResponseEntity.created(new URI("/api/user"))
                .body(accountUsercase.register(request));
    }

    @PutMapping("/api/user")
    public ResponseEntity<?> update(
              @AuthenticationPrincipal UserDetails userDetails
            , @Validated @RequestBody UpdateRequest updateRequest){
        try {
            return ResponseEntity.ok(accountUsercase.update(userDetails.getUsername(), updateRequest));
        } catch (UserNotFoundElementException e) {
            return ResponseEntity.status(e.getCode().getHttpStatus()).body(e.getMessage());
        }

    }

}
