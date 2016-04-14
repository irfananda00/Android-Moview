package project.irfananda.moview.model;

public class Company {
    private Object description;
    private Object headquarters;
    private Object homepage;
    private int id;
    private String logo_path;
    private String name;

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(Object headquarters) {
        this.headquarters = headquarters;
    }

    public Object getHomepage() {
        return homepage;
    }

    public void setHomepage(Object homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
