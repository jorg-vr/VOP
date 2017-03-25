package spring.model;

import model.history.EditEvent;
import spring.controller.UUIDUtil;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class RESTAbstractModel {

    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private String url;

    public RESTAbstractModel() {
    }

    public RESTAbstractModel(UUID uuid, EditEvent createdAt, EditEvent lastUpdated, String url) {
        this.id = UUIDUtil.UUIDToNumberString(uuid);
        //this.createdAt = createdAt.getEditTime(); TODO milestone3
        //this.lastUpdated = lastUpdated.getEditTime(); TODO milestone3
        this.url = url + "/" + id;
    }

    public RESTAbstractModel(UUID uuid, String url) {
        this(uuid, null, null, url);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RESTAbstractModel that = (RESTAbstractModel) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
