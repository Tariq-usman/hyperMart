package com.system.user.menwain.model;

public class UpdateCartQuantity {

    private int c_id;
    private int c_quantity;

    public UpdateCartQuantity(int c_id, int c_quantity) {
        this.c_id = c_id;
        this.c_quantity = c_quantity;
    }

    public int getC_id() {
        return c_id;
    }

    public int getC_quantity() {
        return c_quantity;
    }
}
