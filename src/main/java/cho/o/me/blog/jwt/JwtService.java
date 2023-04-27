package cho.o.me.blog.jwt;

import cho.o.me.blog.exception.KeyGenerationException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    private static final int KEY_SIZE = 256;
    //private static final String SECRET_KEY = "mySecretKey";

    public String token(String email) {
        SecretKey key = generateKey();
        //SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(oneHourLater())
                .signWith(key)
                .compact();
    }
    private SecretKey generateKey() {

        SecureRandom secureRandom = new SecureRandom();
        byte[] seed = secureRandom.generateSeed(16);

        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("HMACSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new KeyGenerationException("Error generating HMACSHA256 key", e);
        }
        keyGenerator.init(KEY_SIZE, secureRandom);

        return keyGenerator.generateKey();
    }


    public Date oneHourLater(){
        return new Date(new Date().getTime() + 3600000);
    }
}
