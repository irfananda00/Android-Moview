package project.irfananda.moview.model;

import io.realm.RealmObject;

public class ProductionCountries{
    private String iso_3166_1;
    private String name;

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}