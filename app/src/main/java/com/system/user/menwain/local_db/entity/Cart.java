package com.system.user.menwain.local_db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Blob;

@Entity
public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private int p_id;
    private String product_image;
    private String product_name;
    private String store_name;
    private float price;
    private float per_unit_price;
    private int quantity;

    public Cart(int p_id, String product_image, String product_name, String store_name, float price, float per_unit_price, int quantity) {
        this.p_id = p_id;
        this.product_name = product_name;
        this.store_name = store_name;
        this.price = price;
        this.per_unit_price = per_unit_price;
        this.quantity = quantity;
        this.product_image = product_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getP_id() {
        return p_id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public float getPrice() {
        return price;
    }

    public float getPer_unit_price() {
        return per_unit_price;
    }

    public int getQuantity() {
        return quantity;
    }
}
