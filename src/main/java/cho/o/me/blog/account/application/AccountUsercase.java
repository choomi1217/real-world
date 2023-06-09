package cho.o.me.blog.account.application;

import cho.o.me.blog.account.domain.Account;
import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.request.LoginRequest;
import cho.o.me.blog.account.ui.request.UpdateRequest;
import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.exception.UserNotFoundElementException;
import cho.o.me.blog.config.JwtService;
import cho.o.me.blog.member.application.MemberService;
import cho.o.me.blog.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountUsercase {

    private final AccountService accountService;
    private final JwtService jwtService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public AccountResponse register(AccountRequest request) {
        Account account = accountService.save(
                new Account(request.email(), passwordEncoder.encode(request.password()), jwtService.emailToToken(request.email()))
        );

        Member member = memberService.save(Member.builder()
                .username(request.username())
                .email(request.email())
                .build()
        );

        return AccountResponse.from(account, member);
    }

    public AccountResponse user(String email)  {
        Account account = accountService.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        Member member = memberService.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        return AccountResponse.from(account, member);
    }


    public AccountResponse login(LoginRequest loginRequest){
        try {
            Account account = accountService.findByEmail(loginRequest.email())
                    .filter(find -> passwordEncoder.matches(loginRequest.password(), find.getPassword()))
                    .orElseThrow(() -> new UserNotFoundElementException("User Not Found"));
            Member member = memberService.findByEmail(loginRequest.email()).orElseThrow(() -> new UserNotFoundElementException("User Not Found"));
            return AccountResponse.from(account, member);
        } catch (UserNotFoundElementException e) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }

    public AccountResponse update(String email, UpdateRequest updateRequest) {
        Account account = accountService.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        Member member = memberService.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        account.update(updateRequest.toAccountWithEncodedPassword(updateRequest, passwordEncoder));
        member.update(updateRequest);
        return AccountResponse.from(account, member);
    }

}
