package com.example.navdrawertest3.RestEntity;

public class Food {
    //[{"calorieAmt":81.0,"category":"fruit","fat":0.0,"foodId":1,"foodName":"apple","servingAmt":1.0,"servingUnit":"each"}
    private Integer foodId;
    private double calorieAmt;
    private double fat;
    private String category;
    private String foodName;
    private double servingAmt;
    private String servingUnit;

    public Food(Integer foodId, double calorieAmt, double fat, String category, String foodName, double servingAmt, String servingUnit) {
        this.foodId = foodId;
        this.calorieAmt = calorieAmt;
        this.fat = fat;
        this.category = category;
        this.foodName = foodName;
        this.servingAmt = servingAmt;
        this.servingUnit = servingUnit;
    }
}
