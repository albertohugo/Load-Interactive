package hugo.alberto.clustermap.Model;

public class EstablishmentModel{

    private String name;
    private String phone;
    private String description;
    private String localname;
    private String lat;
    private String lon;

    public EstablishmentModel() {
    }

    public EstablishmentModel(String name, String phone, String description, String localname, String lat, String lon) {
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.localname = localname;
        this.lat = lat;
        this.lon =  lon ;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalname() {
        return localname;
    }

    public void setLocalname(String localname) {
        this.localname = localname;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

}
