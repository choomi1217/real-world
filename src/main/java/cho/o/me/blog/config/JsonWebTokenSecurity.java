package cho.o.me.blog.config;

import cho.o.me.blog.account.application.AccountService;
import cho.o.me.blog.account.domain.Account;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JsonWebTokenSecurity extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AccountService accountService;
    private final String BEARER_PREFIX = "Token ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token==null || token.isBlank()){
            filterChain.doFilter(request,response);
            return;
        }

        if(!token.startsWith(BEARER_PREFIX)){
            throw new BadCredentialsException("Bad Token");
        }

        String email = jwtService.tokenToEmail(token.substring(BEARER_PREFIX.length()));
        Account account = accountService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        AccountUserDetails accountUserDetails = new AccountUserDetails(account);
        Authentication auth = new UsernamePasswordAuthenticationToken(accountUserDetails, null, accountUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
