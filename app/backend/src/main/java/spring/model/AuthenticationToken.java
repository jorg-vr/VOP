package spring.model;

import spring.controller.UUIDUtil;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by jorg on 3/30/17.
 */
public class AuthenticationToken {
    private UUID acountId;
    private String hash;
    private LocalDateTime expire;

    public AuthenticationToken(UUID acountId, String hash) {
        this.acountId = acountId;
        this.hash = hash;
        this.expire=LocalDateTime.now().plusMinutes(30);
    }

    public AuthenticationToken(String token) {
        String[] tokenparts=token.split(";");
        acountId= UUIDUtil.toUUID(tokenparts[0]);
        hash=tokenparts[1];
        expire=LocalDateTime.parse(tokenparts[2]);
    }

    @Override
    public String toString() {
        return UUIDUtil.UUIDToNumberString(acountId)+";"+
                hash+";"+
                expire;
    }

    public UUID getAcountId() {
        return acountId;
    }

    public void setAcountId(UUID acountId) {
        this.acountId = acountId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public LocalDateTime getExpire() {
        return expire;
    }

    public void setExpire(LocalDateTime expire) {
        this.expire = expire;
    }
}
