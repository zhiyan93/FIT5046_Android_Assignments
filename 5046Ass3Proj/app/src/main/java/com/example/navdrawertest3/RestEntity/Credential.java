package com.example.navdrawertest3.RestEntity;

public class Credential {
    private String loginName;
    private String pwHash;
    private String signupDate;
    private userId userId;

    public Credential(String loginName,String pwHash,String signupDate,userId userId)
    {
        this.loginName = loginName;
        this.pwHash = pwHash;
        this.signupDate = signupDate;
        this.userId = userId;
    }
}