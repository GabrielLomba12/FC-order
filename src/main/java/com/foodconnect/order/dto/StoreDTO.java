package com.foodconnect.order.dto;

import com.foodconnect.order.model.StoreModel;

public class StoreDTO {
    private String name;
    private String FoodCourt;

    public StoreDTO() {
    }

    public StoreDTO(StoreModel model) {
        this.name = model.getName();
        this.FoodCourt = model.getFoodCourt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoodCourt() {
        return FoodCourt;
    }

    public void setFoodCourt(String foodCourt) {
        FoodCourt = foodCourt;
    }
}
