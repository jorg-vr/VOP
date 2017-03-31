package spring.model;

import spring.controller.UUIDUtil;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by jorg on 3/30/17.
 */
public class AuthenticationToken {
    private UUID acountId;
    private UUID functionId;
    private String hash;
    private LocalDateTime expire;

    public AuthenticationToken(String token) {
        String[] tokenparts=token.split(":");
        acountId= UUIDUtil.toUUID(tokenparts[0]);
        functionId=UUIDUtil.toUUID(tokenparts[1]);
        hash=tokenparts[2];
        expire=LocalDateTime.parse(tokenparts[3]);
    }

    @Override
    public String toString() {
        return UUIDUtil.UUIDToNumberString(acountId)+":"+
                UUIDUtil.UUIDToNumberString(functionId)+":"+
                hash+":"+
                expire;
    }

    public UUID getAcountId() {
        return acountId;
    }

    public void setAcountId(UUID acountId) {
        this.acountId = acountId;
    }

    public UUID getFunctionId() {
        return functionId;
    }

    public void setFunctionId(UUID functionId) {
        this.functionId = functionId;
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
