package org.example.db;

import javax.persistence.*;


@Entity
@Table(name="Countries")
public class Country {
    public Country(){

    }

    @Id
    private String id;
    @Column(name="Region")
    private String region;
    @Column(name="Population")
    private String population;


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

    public Country(String id, String region, String population){
        this.id = id;
        this.region = region;
        this.population = population;
    }


    @Override
    public String toString() {

        return "Country [id=" + id + ", Region=" + region + ", Population=" + population + "]";
    }
}


