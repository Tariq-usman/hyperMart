package com.system.user.menwain.responses.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculateShippingCostResponse {
    @SerializedName("shippingcost")
    @Expose
    private Integer shippingcost;

    public Integer getShippingcost() {
        return shippingcost;
    }

    public void setShippingcost(Integer shippingcost) {
        this.shippingcost = shippingcost;
    }

}

