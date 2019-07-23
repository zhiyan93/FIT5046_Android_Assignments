package com.example.navdrawertest3.RestEntity;

public class Consumption {
    private Integer consumId;
    private String consumDate;
    private double quantity;
    private foodId foodId;
    private userId userId;

    public Consumption(Integer consumId, String consumDate, double quantity, foodId foodId, userId userId) {
        this.consumId = consumId;
        this.consumDate = consumDate;
        this.quantity = quantity;
        this.foodId = foodId;
        this.userId = userId;
    }
}
