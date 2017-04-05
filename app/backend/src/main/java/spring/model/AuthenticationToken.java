package spring.model;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import spring.controller.UUIDUtil;
import spring.exceptions.NotAuthorizedException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * Created by jorg on 3/30/17.
 */
public class AuthenticationToken {

    private UUID acountId;
    private LocalDateTime expire;

    public AuthenticationToken(UUID acountId) {
        this.acountId = acountId;
        this.expire=LocalDateTime.now().plusMinutes(30);
    }

    public AuthenticationToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            expire=toLocalDate(jwt.getExpiresAt());
            acountId=UUIDUtil.toUUID(jwt.getSubject());
        } catch (JWTDecodeException exception){
            throw new NotAuthorizedException();
        }
    }

    private Date fromLocalDate(LocalDateTime ldt){
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime toLocalDate(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public String toString() {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withSubject(UUIDUtil.UUIDToNumberString(acountId))
                    .withExpiresAt(fromLocalDate(expire))
                    .sign(algorithm);
            return token;
        } catch (UnsupportedEncodingException | JWTCreationException exception){
            throw new RuntimeException(exception);
        }
    }

    public UUID getAcountId() {
        return acountId;
    }

    public void setAcountId(UUID acountId) {
        this.acountId = acountId;
    }

    public LocalDateTime getExpire() {
        return expire;
    }

    public void setExpire(LocalDateTime expire) {
        this.expire = expire;
    }
}
