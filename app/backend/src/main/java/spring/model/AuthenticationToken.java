package spring.model;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import controller.exceptions.InvalidTokenException;
import util.UUIDUtil;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static main.BackendApplication.DISABLE_AUTH;

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


    private UUID accountId;

    public AuthenticationToken(UUID accountId) {
        this.accountId = accountId;
    }

    /**
     *
     * @param token string representation of token
     * @throws InvalidTokenException token is not valid
     */
    public AuthenticationToken(String token) throws InvalidTokenException {
        if (DISABLE_AUTH) {
            accountId = new UUID(0, 0);
            return;
        }
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            accountId =UUIDUtil.toUUID(jwt.getSubject());
        } catch (JWTDecodeException | SignatureVerificationException | InvalidClaimException exception){
            throw new InvalidTokenException();
        }
    }

    private Date fromLocalDate(LocalDateTime ldt){
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public String toString() {
        try {
            Algorithm algorithm = Algorithm.RSA256(privateKey);
            String token = JWT.create()
                    .withSubject(UUIDUtil.UUIDToNumberString(accountId))
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(900)))
                    .sign(algorithm);
            return token;
        } catch ( JWTCreationException exception){
            throw new RuntimeException(exception);
        }
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}
