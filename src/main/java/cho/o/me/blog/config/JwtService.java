package cho.o.me.blog.config;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    private static final String SECRET_KEY = "mXGi8&9cLq!dEW`m^9VN?R9cGl~z=&03?vb#-RCP|yW4*-dikEAt%{8SE*%yNLU";

    public String emailToToken(String email) {
        SecretKey key = generateKey();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(oneHourLater())
                .signWith(key)
                .compact();
    }

    public String tokenToEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


    public Date oneHourLater(){
        return new Date(new Date().getTime() + 3600000);
    }
}
