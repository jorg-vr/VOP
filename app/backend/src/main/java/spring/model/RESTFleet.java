package spring.model;

import model.identity.Company;

/**
 * Created by jorg on 3/12/17.
 */
public class RESTFleet {
    private String id;
    private String company;
    private String name;
    private String createdAt;
    private String updatedAt;
    private String lastUpdatedBy;
    private String url;

    public RESTFleet() {
    }

    public RESTFleet(String id, String company, String name, String createdAt, String updatedAt, String lastUpdatedBy, String url) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastUpdatedBy = lastUpdatedBy;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
