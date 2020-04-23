package com.system.user.menwain.others;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefrences {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Context context;
    Object o;

    // Constructor
    public Prefrences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("fragment_status", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setToken(String token) {
        editor.putString("token", token);
        editor.apply();
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    public void setPaymentStatus(int paymentStatus){
        editor.putInt("payment_status", paymentStatus);
        editor.apply();
        editor.commit();
    }
    public int getPaymentStatus() {
        return sharedPreferences.getInt("payment_status", 0);
    }

    public void setPayRBtnStatus(int rBtnStatus){
        editor.putInt("pay_r_btn_checked", rBtnStatus);
        editor.apply();
        editor.commit();
    }
    public int getPayRBtnStatus() {
        return sharedPreferences.getInt("pay_r_btn_checked", 0);
    }


    public void setBottomNavStatus(int bottomNavStatus){
        editor.putInt("Bottom_Nav_status", bottomNavStatus);
        editor.apply();
        editor.commit();
    }
    public int getBottomNavStatus() {
        return sharedPreferences.getInt("Bottom_Nav_status", 0);
    }

    public void setHomeFragStatus(int fragStatus) {
        editor.putInt("home_frag_status", fragStatus);
        editor.apply();
        editor.commit();
    }

    public int getHomeFragStatus() {
        return sharedPreferences.getInt("home_frag_status", 0);
    }

    public void setCategoryFragStatus(int fragStatus) {
        editor.putInt("category_frag_status", fragStatus);
        editor.apply();
        editor.commit();
    }

    public int getCategoryFragStatus() {
        return sharedPreferences.getInt("category_frag_status", 0);
    }

    public void setCartFragStatus(int fragStatus) {
        editor.putInt("cart_frag_status", fragStatus);
        editor.apply();
        editor.commit();
    }

    public int getCartFragStatus() {
        return sharedPreferences.getInt("cart_frag_status", 0);
    }

    public void setMyListFragStatus(int fragStatus) {
        editor.putInt("my_list_frag_status", fragStatus);
        editor.apply();
        editor.commit();
    }

    public int getMyListFragStatus() {
        return sharedPreferences.getInt("my_list_frag_status", 0);
    }

    public void setMorFragStatus(int fragStatus) {
        editor.putInt("more_frag_status", fragStatus);
        editor.apply();
        editor.commit();
    }

    public int getMoreFragStatus() {
        return sharedPreferences.getInt("more_frag_status", 0);
    }

    public void setMoreStoresFragStatus(int fragStatus) {
        editor.putInt("store_frag_status", fragStatus);
        editor.apply();
        editor.commit();
    }

    public int getMoreStoresFragStatus() {
        return sharedPreferences.getInt("store_frag_status", 0);
    }
    public void setMoreOrdersFragStatus(int fragStatus) {
        editor.putInt("store_frag_status", fragStatus);
        editor.apply();
        editor.commit();
    }

    public int getMoreOrdersFragStatus() {
        return sharedPreferences.getInt("store_frag_status", 0);
    }

    public void setProfileFragStatus(int fragStatus) {
        editor.putInt("profile_frag_status", fragStatus);
        editor.apply();
        editor.commit();
    }

    public int getProfileFragStatus() {
        return sharedPreferences.getInt("profile_frag_status", 0);
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    public void setDeliveryAddressId(int address_id) {
        editor.putInt("address_id", address_id);
        editor.apply();
        editor.commit();
    }

    public int getDeliveryAddressId() {
        return sharedPreferences.getInt("address_id", 0);
    }

    public void setStoreId(int store_id) {
        editor.putInt("store_id", store_id);
        editor.apply();
        editor.commit();
    }

    public int getStoreId() {
        return sharedPreferences.getInt("store_id", 0);
    }

    public void setTotalPrice(int total_price) {
        editor.putInt("total_price", total_price);
        editor.apply();
        editor.commit();
    }

    public int getTotalPrice() {
        return sharedPreferences.getInt("total_price", 0);
    }

    public void setShippingCost(int shipping_cost) {
        editor.putInt("shipping_cost", shipping_cost);
        editor.apply();
        editor.commit();
    }

    public int getShippingCost() {
        return sharedPreferences.getInt("shipping_cost", 0);
    }

    public void setDateTime(String date_time) {
        editor.putString("date_time", date_time);
        editor.apply();
        editor.commit();
    }

    public String getDateTime() {
        return sharedPreferences.getString("date_time", "");
    }

    public void setMoreStoreId(int more_store_id) {
        editor.putInt("more_store_id", more_store_id);
        editor.apply();
        editor.commit();
    }

    public int getMoreStoreId() {
        return sharedPreferences.getInt("more_store_id", 0);
    }

    public void setDeliverAddress(String delivery_address) {
        editor.putString("delivery_address", delivery_address);
        editor.apply();
        editor.commit();
    }
    public String getDeliveryAddress(){
        return sharedPreferences.getString("delivery_address","");
    }
}