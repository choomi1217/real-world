package cho.o.me.blog.config;

import cho.o.me.blog.account.application.AccountService;
import cho.o.me.blog.account.domain.Account;
import cho.o.me.blog.account.ui.AccountController;
import cho.o.me.blog.exception.UserNotFoundElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountUserDetailService implements UserDetailsService {

    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            return new AccountUserDetails(accountService.findByEmail(username));
        } catch (UserNotFoundElementException e) {
            throw new RuntimeException(e);
        }

    }
}
