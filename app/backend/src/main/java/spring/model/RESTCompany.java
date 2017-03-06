package spring.model;

/**
 * Created by jorg on 3/6/17.
 */
public class RESTCompany {


    private int id;
    private String name;
    private String vat_number;
    private int phone_number;
    private RESTAddress address;
    private String created_at;
    private String last_updated;
    private String url;

    public RESTCompany(int id, String name, String vat_number, int phone_number, RESTAddress address, String created_at, String last_updated, String url) {
        this.id = id;
        this.name = name;
        this.vat_number = vat_number;
        this.phone_number = phone_number;
        this.address = address;
        this.created_at = created_at;
        this.last_updated = last_updated;
        this.url = url;
    }

    public RESTCompany() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVat_number() {
        return vat_number;
    }

    public void setVat_number(String vat_number) {
        this.vat_number = vat_number;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public RESTAddress getAddress() {
        return address;
    }

    public void setAddress(RESTAddress address) {
        this.address = address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


