package org.covidtestingplatform.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class User {
    private String id;
    private String name;
    private Coordinates location;
    private boolean vaccinationProvided;
    private List<TestingCentre> waitingIn;
    private String vaccinatedFrom;

    public User(String name, Coordinates location){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.location = location;
        vaccinationProvided = false;
        waitingIn = new ArrayList<>();
        vaccinatedFrom="";
    }

    public synchronized boolean vaccinate(String testCenterName) {
        if(this.vaccinationProvided) return false;
        vaccinationProvided = true;
        waitingIn.clear();
        this.vaccinatedFrom=testCenterName;
        System.out.println(String.format("User %s vaccinated from %s " , this.name,  this.vaccinatedFrom));
        return true;
    }
}
