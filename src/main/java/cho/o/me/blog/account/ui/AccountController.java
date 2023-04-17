package cho.o.me.blog.account.ui;

import cho.o.me.blog.account.application.AccountUsercase;
import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.request.LoginRequest;
import cho.o.me.blog.account.ui.request.UpdateRequest;
import cho.o.me.blog.account.ui.response.AccountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class AccountController {

    AccountUsercase accountUsercase;
    @PostMapping("/api/users/login")
    public ResponseEntity<AccountResponse> login(@Validated @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(accountUsercase.login(loginRequest));
    }

    @GetMapping("/api/user")
    public ResponseEntity<AccountResponse> user(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(accountUsercase.user(userDetails.getUsername()));
    }

    @PostMapping("/api/users")
    public ResponseEntity<AccountResponse> register(@Validated @RequestBody AccountRequest request) throws URISyntaxException {
        return ResponseEntity.created(new URI("/api/user"))
                .body(accountUsercase.register(request));
    }

    @PutMapping("/api/user")
    public ResponseEntity<AccountResponse> update(
              @AuthenticationPrincipal UserDetails userDetails
            , @Validated @RequestBody UpdateRequest updateRequest){
        return ResponseEntity.ok(accountUsercase.update(userDetails.getUsername(), updateRequest));
    }

}
