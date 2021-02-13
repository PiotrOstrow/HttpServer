package org.example.db;

import javax.persistence.*;


@Entity
@Table(name = "Countries", schema = "dbo")
public class Country {

    @Id
    @Column(name = "Country")
    private String id;

    private String region;
    private String population;

    public Country() {

    }

    public Country(String id, String region, String population){
        this.id = id;
        this.region = region;
        this.population = population;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRegion(String s) {
        return region;
    }
    public void setRegion(String name) {
        this.region = region;
    }
    public String getPopulation() {
        return population;
    }
    public void setPopulation(String population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id='" + id + '\'' +
                ", region='" + region + '\'' +
                ", population='" + population + '\'' +
                '}';
    }
}


