package com.spring.boot.social.config.security;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.exceptions.ExpiredTokenException;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.setting.JWTToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Component
public class TokenHandler {
    private String secret;
    private Duration time;
    private JwtBuilder jwtBuilder;
    private JwtParser jwtParser;
    @Autowired
    private AccountService accountService;

    public TokenHandler(JWTToken jwtToken) {
        this.secret = jwtToken.getSecret();
        this.time = jwtToken.getTime();
        Key key = Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
        this.jwtBuilder = Jwts.builder().signWith(key);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    //generate token method
    public String generateToken(AccountDto accountDto) {
        this.jwtBuilder.setSubject(accountDto.getEmail());
        Date now = new Date();
        this.jwtBuilder.setIssuedAt(now);
        this.jwtBuilder.setExpiration(createExpirationDate(now));
        //this.jwtBuilder.claim("phoneNumber", accountDto.getUserDetailsDto().getPhoneNumber());
        return this.jwtBuilder.compact();
    }

    //validate token
    public AccountDto validateToken(String token) {
        try {
            if (this.jwtParser.isSigned(token)) {
                Claims claims = this.jwtParser.parseClaimsJws(token).getBody();
                String email = claims.getSubject();
                Date expirationDate = claims.getExpiration();
                Date issuedDate = claims.getIssuedAt();
                AccountDto accountDto = accountService.getAccountByEmail(email);
                boolean valid = expirationDate.after(new Date()) && issuedDate.before(expirationDate) && Objects.nonNull(accountDto);
                return valid ? accountDto : null;
            }
        } catch (Exception e) {
            throw new ExpiredTokenException(e.getMessage());
        }
        return null;
    }

    private Date createExpirationDate(Date date) {
        return Date.from(date.toInstant().plus(time));
    }
}
