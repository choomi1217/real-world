package cho.o.me.blog.config;

import cho.o.me.blog.account.application.AccountService;
import cho.o.me.blog.account.ui.AccountController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class AccountUserDetailService implements UserDetailsService {

    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        new AccountUserDetails(accountService.user(username));
        return null;
    }
}
