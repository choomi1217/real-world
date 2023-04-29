package cho.o.me.blog.config;

import cho.o.me.blog.account.application.AccountService;
import cho.o.me.blog.account.domain.Account;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonWebTokenSecurity extends OncePerRequestFilter {
    private JwtService jwtService;
    private AccountService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader.isBlank()){
            filterChain.doFilter(request,response);
        }
        String email = jwtService.email(authorizationHeader);
        Account account = accountService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        AccountUserDetails accountUserDetails = new AccountUserDetails(account);
    }
}
