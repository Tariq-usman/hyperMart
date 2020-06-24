package com.system.user.menwain.local_db.model;

public class UpdateProductQuantity {

    private int p_id;
    private int p_quantity;
    private int p_price;

    public UpdateProductQuantity(int p_id, int p_quantity,int p_price) {
        this.p_id = p_id;
        this.p_quantity = p_quantity;
        this.p_price = p_price;
    }

    public int getP_id() {
        return p_id;
    }

    public int getP_quantity() {
        return p_quantity;
    }

    public int getP_price() {
        return p_price;
    }
}
