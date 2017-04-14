package spring.model;

import model.account.Function;
import model.history.EditEvent;
import model.history.EditableObject;
import util.UUIDUtil;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This abstract class contains some fields that (almost) every RESTModel has.
 */
public abstract class RESTAbstractModel<T extends EditableObject> {

    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private String url;

    public RESTAbstractModel() {
    }

    /**
     * @param uuid uuid of the model that the RESTModel is based on, it will be translated to a string in the constructor
     * @param createdAt
     * @param lastUpdated
     * @param url the base URL of the RESTModel.
     *            Appending the id to the url will be handled in the constructor, so it should NOT be /vehicles/123 for example but /vehicles.
     *            If url does not start with '/' it will be added
     */
    public RESTAbstractModel(UUID uuid, EditEvent createdAt, EditEvent lastUpdated, String url) {
        this.id = UUIDUtil.UUIDToNumberString(uuid);
        //this.createdAt = createdAt.getEditTime(); TODO milestone3
        //this.lastUpdated = lastUpdated.getEditTime(); TODO milestone3
        if (! url.startsWith("/")) {
            url = "/" + url;
        }
        this.url = url + "/" + id;
    }

    public RESTAbstractModel(UUID uuid, String url) {
        this(uuid, null, null, url);
    }

    public abstract T translate(Function function);

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
