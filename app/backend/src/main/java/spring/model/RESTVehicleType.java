package spring.model;

/**
 * Created by jorg on 3/14/17.
 */
public class RESTVehicleType  {
    private String id;
    private String name;

    public RESTVehicleType() {
    }

    public RESTVehicleType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
