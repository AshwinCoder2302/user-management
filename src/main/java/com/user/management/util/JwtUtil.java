package com.user.management.util;

import com.user.management.constant.Constant;
import com.user.management.exception.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey key;

    // Initializes the key after the class is instantiated and the jwtSecret is injected,
    // preventing the repeated creation of the key and enhancing performance
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Generate JWT token
    public String generateToken(String username, int tokenExpiration, String issuer) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer(issuer)
                .setExpiration(new Date((new Date()).getTime() + tokenExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Get username from JWT token
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getPasswordFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate JWT token
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | SignatureException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, Constant.INVALID_JWT_SIGNATURE);
        }
        catch (MalformedJwtException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, Constant.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, Constant.JWT_TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, Constant.JWT_TOKEN_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, Constant.JWT_TOKEN_EMPTY_CLAIMS);
        }
    }
}

