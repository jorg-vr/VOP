package spring.model;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import spring.controller.UUIDUtil;
import spring.exceptions.NotAuthorizedException;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * Created by jorg on 3/30/17.
 */
public class AuthenticationToken {
    private static RSAPrivateKey privateKey;
    private static RSAPublicKey publicKey;
    static {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            publicKey = (RSAPublicKey) kp.getPublic();
            privateKey = (RSAPrivateKey) kp.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private UUID acountId;
    private LocalDateTime expire;

    public AuthenticationToken(UUID acountId) {
        this.acountId = acountId;
        this.expire=LocalDateTime.now().plusMinutes(30);
    }

    public AuthenticationToken(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
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
            Algorithm algorithm = Algorithm.RSA256(privateKey);
            String token = JWT.create()
                    .withSubject(UUIDUtil.UUIDToNumberString(acountId))
                    .withExpiresAt(fromLocalDate(expire))
                    .sign(algorithm);
            return token;
        } catch ( JWTCreationException exception){
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
