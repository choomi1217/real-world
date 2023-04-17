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
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    AccountRepository accountRepository;


    public Account save(Account account) {
        
    }

    public Account findByEmail(String email) {
    }
}
