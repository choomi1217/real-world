package cho.o.me.blog.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtService {
    private static final String SECRET_KEY = "mySecretKey";

    public String token(String email) {

        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(oneHourLater())
                .signWith(key)
                .compact();

    }

    public Date oneHourLater(){
        return new Date(new Date().getTime() + 3600000);
    }
}
