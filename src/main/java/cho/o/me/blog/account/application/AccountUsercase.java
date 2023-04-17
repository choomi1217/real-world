package cho.o.me.blog.account.application;

import cho.o.me.blog.account.domain.Account;
import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.request.LoginRequest;
import cho.o.me.blog.account.ui.request.UpdateRequest;
import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.jwt.JwtService;
import cho.o.me.blog.member.application.MemberService;
import cho.o.me.blog.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountUsercase {

    private final AccountService accountService;
    private final JwtService jwtService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    public AccountResponse register(AccountRequest request) {
        Account account = accountService.save(
                new Account(request.getEmail(), passwordEncoder.encode(request.getPassword()))
        );

        Member member = memberService.save(Member.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .build());

        return accountResponse(account, member);
    }

    public AccountResponse user(String email) {
        Account account = accountService.findByEmail(email);
        Member member = memberService.findByEmail(email);
        return accountResponse(account, member);
    }


    public AccountResponse login(LoginRequest loginRequest) {
        Account account = accountService.findByEmail(loginRequest.getEmail());
        Member member = memberService.findByEmail(loginRequest.getEmail());
        if (passwordEncoder.matches(account.getPassword(), loginRequest.getPassword())) {
            throw new IllegalArgumentException("password is not matched");
        }

        return accountResponse(account, member);
    }

    public AccountResponse update(String email, UpdateRequest updateRequest) {
        Account account = accountService.findByEmail(email);
        account.update(updateRequest.encoding(passwordEncoder));
        Member member = memberService.findByEmail(email);
        member.update(updateRequest);
        return accountResponse(account, member);
    }


    private AccountResponse accountResponse(Account account, Member member) {
        return AccountResponse.builder()
                .username(member.getUsername())
                .email(account.getEmail())
                .token(jwtService.token(account.getEmail()))
                .bio(member.getBio())
                .image(member.getImage())
                .build();
    }

}
