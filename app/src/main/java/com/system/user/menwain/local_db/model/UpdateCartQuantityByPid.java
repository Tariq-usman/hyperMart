package com.system.user.menwain.local_db.model;

public class UpdateCartQuantityByPid {

    private int c_id;
    private int c_quantity;
    private float per_unit_price;

    public UpdateCartQuantityByPid(int c_id, int c_quantity, float per_unit_price) {
        this.c_id = c_id;
        this.c_quantity = c_quantity;
        this.per_unit_price = per_unit_price;
    }

    public int getC_id() {
        return c_id;
    }

    public int getC_quantity() {
        return c_quantity;
    }

    public float getPer_unit_price() {
        return per_unit_price;
    }
}
