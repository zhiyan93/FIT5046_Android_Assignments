package com.example.navdrawertest3.RestEntity;

import java.util.Collection;
import java.util.Date;

public class Usr {
    private Integer userId;
    private String firstName;
    private String surname;
    private String email;
    private String dob;
    private double height;
    private double weight;
    private Character gender;
    private String address;
    private String postcode;
    private int activityLevel;
    private int stepsMile;
    private Collection<Credential> credentialCollection;

    public Usr(Integer userId, String firstName, String surname, String email, String dob, double height, double weight, Character gender, String address, String postcode, int activityLevel, int stepsMile) {
        this.userId = userId;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.activityLevel = activityLevel;
        this.stepsMile = stepsMile;
        //this.credentialCollection= credentialCollection;
    }

    public void setUserId(Integer id){
        this.userId = id;
    }
}

