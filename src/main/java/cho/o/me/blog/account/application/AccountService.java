package cho.o.me.blog.account.application;

import cho.o.me.blog.account.domain.Account;
import cho.o.me.blog.account.repository.AccountRepository;
import cho.o.me.blog.account.ui.request.AccountRequest;
import cho.o.me.blog.account.ui.request.LoginRequest;
import cho.o.me.blog.account.ui.request.UpdateRequest;
import cho.o.me.blog.account.ui.response.AccountResponse;
import cho.o.me.blog.jwt.JwtService;
import cho.o.me.blog.member.domain.Member;
import cho.o.me.blog.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AccountResponse register(AccountRequest request) {
        Account account = accountRepository.save(
                new Account(request.getEmail(), passwordEncoder.encode(request.getPassword()))
        );

        Member member = memberRepository.save(Member.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .build());

        return accountResponse(account, member);
    }

    public AccountResponse user(String email) {
        Account account = accountRepository.findByEmail(email);
        Member member = memberRepository.findByEmail(email);
        return accountResponse(account, member);
    }


    public AccountResponse login(LoginRequest loginRequest) {
        Account account = accountRepository.findByEmail(loginRequest.getEmail());
        Member member = memberRepository.findByEmail(loginRequest.getEmail());
        if (passwordEncoder.matches(account.getPassword(), loginRequest.getPassword())) {
            throw new IllegalArgumentException("password is not matched");
        }

        return accountResponse(account, member);
    }

    public AccountResponse update(String email, UpdateRequest updateRequest) {
        Account account = accountRepository.update(email, accountRepository.findByEmail(email));
        Member member = memberRepository.update(email, memberRepository.findByEmail(email));
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
