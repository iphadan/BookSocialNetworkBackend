package bsn.backend.SECURITY;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

public class JwtService {
//    @Value("${secretKey}")
    String secretKet;
//    @Value("${jwtExpiration}")
    Long jwtExpiration;
    public String extractUserName(String jwt) {
        return null;
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<String,Object>(),userDetails);
    }
    public String generateToken(HashMap<String,Object> extraClaims,UserDetails userDetails){
        return buildToken(extraClaims,userDetails);

    }

    private String buildToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
var authorities = userDetails
        .getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder().setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .signWith(getSecretKey())
        .claim("authorities",authorities)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+ jwtExpiration) )
        .compact();

    }
    public Key getSecretKey(){
        byte[] keyByte = Decoders.BASE64.decode(secretKet);
        return Keys.hmacShaKeyFor(keyByte);

    }

}
