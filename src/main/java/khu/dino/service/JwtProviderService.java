package khu.dino.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import khu.dino.exception.auth.JwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class JwtProviderService {

    @Value("${jwt.secret}")
    private String secret;

    private Key secret_key;


    @Value("${jwt.access.expiration}")
    private Long ACCESS_TOKEN_EXPIRATION_PERIOD;



    @Value("${jwt.access.header}")
    private String ACCESS_HEADER_KEY;

    private static final String BEARER = "Bearer ";



    private final GrantedAuthoritiesMapper authoritiesMapper = new SimpleAuthorityMapper();

    @PostConstruct
    protected void init() {
        log.info("secret_key Base64 인코딩시작");
        log.info("Original Secret_Key : " + secret_key);
        this.secret_key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        log.info("Encoded Base64 Secret_Key : " + secret_key);
        log.info("secretKey 초기화 완료");
    }

    public String extractOAuth2Id(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(secret_key)
                .build()
                .parseClaimsJws(jwt)
                .getBody().getSubject();

    }

    public Optional<String> extractAccessToken(HttpServletRequest request) throws IOException, ServletException {
        return Optional.ofNullable(request.getHeader(ACCESS_HEADER_KEY)).filter(accessToken -> accessToken.startsWith(BEARER)).map(accessToken -> accessToken.replace(BEARER, ""));
    }


    public Long getExpireTime(String token) {
        Date expirationDate =  Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody().getExpiration();
        long now = new Date().getTime();
        return ((expirationDate.getTime() - now) % 1000) + 1;
    }



    public String generateAccessToken(String OAuth2Id, String role) {
        Claims claims = Jwts.claims().setSubject(OAuth2Id);
        claims.put("role", role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(secret_key, SignatureAlgorithm.HS256)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_PERIOD))
                .compact(); //토큰생성
    }

    //인증 객체 생성
    public Authentication getAuthentication(UserDetails userDetails) {
        log.info("토큰 인증 정보 조회 시작");
        log.info("UserDetails UserName : {}",
                userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, null,
                authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token);
            log.info("토큰 유효 체크 완료");
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtAuthenticationException e) {
            log.info("JWT 토큰이 유효하지 않습니다.");
            throw new JwtAuthenticationException("JWT 토큰이 유효하지 않습니다.");
        }
    }

}
