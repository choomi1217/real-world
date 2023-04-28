package cho.o.me.blog.account.application;

import cho.o.me.blog.account.domain.Account;
import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.request.LoginRequest;
import cho.o.me.blog.account.ui.request.UpdateRequest;
import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.exception.UserNotFoundElementException;
import cho.o.me.blog.jwt.JwtService;
import cho.o.me.blog.member.application.MemberService;
import cho.o.me.blog.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
                new Account(request.email(), passwordEncoder.encode(request.password()))
        );

        Member member = memberService.save(Member.builder()
                .username(request.username())
                .email(request.email())
                .build());

        return accountResponse(account, member);
    }

    public AccountResponse user(String email) throws UserNotFoundElementException {
        Account account = accountService.findByEmail(email).orElseThrow();
        Member member = memberService.findByEmail(email);
        return accountResponse(account, member);
    }


    public AccountResponse login(LoginRequest loginRequest){
        try {
            Account account = accountService.findByEmail(loginRequest.getEmail())
                    .filter(find -> passwordEncoder.matches(loginRequest.getPassword(), find.getPassword()))
                    .orElseThrow(() -> new UserNotFoundElementException("User Not Found"));
            Member member = memberService.findByEmail(loginRequest.getEmail());
            return accountResponse(account, member);
        } catch (UserNotFoundElementException e) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }

    public AccountResponse update(String email, UpdateRequest updateRequest) throws UserNotFoundElementException {
        Account account = accountService.findByEmail(email).orElseThrow();
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
