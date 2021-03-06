package com.system.user.menwain.others;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Context context;
    Object o;

    // Constructor
    public Preferences(Context context) {
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


    public void setPaymentStatus(int paymentStatus) {
        editor.putInt("payment_status", paymentStatus);
        editor.apply();
        editor.commit();
    }

    public int getPaymentStatus() {
        return sharedPreferences.getInt("payment_status", 0);
    }

    public void setSearchStatus(int searchStatus) {
        editor.putInt("searchStatus", searchStatus);
        editor.apply();
        editor.commit();
    }

    public int getSearchStatus() {
        return sharedPreferences.getInt("searchStatus", 0);
    }

    public void setSearchByName(String searchByName) {
        editor.putString("searchByName", searchByName);
        editor.apply();
        editor.commit();
    }

    public String getSearchByName() {
        return sharedPreferences.getString("searchByName", "");
    }
    public void setSearchByCode(String searchByCode) {
        editor.putString("searchByCode", searchByCode);
        editor.apply();
        editor.commit();
    }

    public String getSearchByCode() {
        return sharedPreferences.getString("searchByCode", "");
    }

    public void setPaymentMethodId(int payment_method_id) {
        editor.putInt("payment_method_id", payment_method_id);
        editor.apply();
        editor.commit();
    }

    public int getPaymentMethodId() {
        return sharedPreferences.getInt("payment_method_id", 0);
    }

    public void setRadius(int radius) {
        editor.putInt("radius", radius);
        editor.apply();
        editor.commit();
    }

    public int getRadius() {
        return sharedPreferences.getInt("radius", 0);
    }

    public void setPayRBtnStatus(int rBtnStatus) {
        editor.putInt("pay_r_btn_checked", rBtnStatus);
        editor.apply();
        editor.commit();
    }

    public int getPayRBtnStatus() {
        return sharedPreferences.getInt("pay_r_btn_checked", 0);
    }


    public void setBottomNavStatus(int bottomNavStatus) {
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

    public void setSuperCatId(int super_cat_id) {
        editor.putInt("super_cat_id", super_cat_id);
        editor.apply();
        editor.commit();
    }

    public int getSuperCatId() {
        return sharedPreferences.getInt("super_cat_id", 0);
    }

    public void setTotalAmount(int total_amount) {
        editor.putInt("total_amount", total_amount);
        editor.apply();
        editor.commit();
    }

    public int getTotalAmount() {
        return sharedPreferences.getInt("total_amount", 0);
    }

    public void setCatsId(int cat_id) {
        editor.putInt("cat_id", cat_id);
        editor.apply();
        editor.commit();
    }

    public int getCatsId() {
        return sharedPreferences.getInt("cat_id", 0);
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

    public void setMoreOrdersStatus(int MoreOrdersStatus) {
        editor.putInt("MoreOrdersStatus", MoreOrdersStatus);
        editor.apply();
        editor.commit();
    }

    public int getMoreOrdersStatus() {
        return sharedPreferences.getInt("MoreOrdersStatus", 0);
    }

    public void setMoreOrdersStatusName(String MoreOrdersStatusName) {
        editor.putString("MoreOrdersStatusName", MoreOrdersStatusName);
        editor.apply();
        editor.commit();
    }

    public String getMoreOrdersStatusName() {
        return sharedPreferences.getString("MoreOrdersStatusName", "");
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

    public void setStoreName(String storeName) {
        editor.putString("storeName", storeName);
        editor.apply();
        editor.commit();
    }

    public String getStoreName() {
        return sharedPreferences.getString("storeName", "");
    }

    public void setStoreImage(String storeImage) {
        editor.putString("storeImage", storeImage);
        editor.apply();
        editor.commit();
    }

    public String getStoreImage() {
        return sharedPreferences.getString("storeImage", "");
    }

    public void setStoreLocation(String storeLocation) {
        editor.putString("storeLocation", storeLocation);
        editor.apply();
        editor.commit();
    }

    public String getStoreLocation() {
        return sharedPreferences.getString("storeLocation", "");
    }


    public void setWishListName(String setWishListName) {
        editor.putString("setWishListName", setWishListName);
        editor.apply();
        editor.commit();
    }

    public String getWishListName() {
        return sharedPreferences.getString("setWishListName", "");
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

    public void setShippingId(int shipping_cost_id) {
        editor.putInt("shipping_cost_id", shipping_cost_id);
        editor.apply();
        editor.commit();
    }

    public int getShippingId() {
        return sharedPreferences.getInt("shipping_cost_id", 0);
    }


    public void setDateTime(String date_time) {
        editor.putString("date_time", date_time);
        editor.apply();
        editor.commit();
    }

    public String getDateTime() {
        return sharedPreferences.getString("date_time", "");
    }

    public void setTimeSlotId(int time_slot_id) {
        editor.putInt("time_slot_id", time_slot_id);
        editor.apply();
        editor.commit();
    }

    public int getTimeSlotId() {
        return sharedPreferences.getInt("time_slot_id", 0);
    }

    public void setMoreStoreId(int more_store_id) {
        editor.putInt("more_store_id", more_store_id);
        editor.apply();
        editor.commit();
    }

    public int getMoreStoreId() {
        return sharedPreferences.getInt("more_store_id", 0);
    }

    public void setMoreOrderId(int more_order_id) {
        editor.putInt("more_order_id", more_order_id);
        editor.apply();
        editor.commit();
    }

    public int getMoreOrderId() {
        return sharedPreferences.getInt("more_order_id", 0);
    }

    public void setOrderStatus(int order_status) {
        editor.putInt("order_status", order_status);
        editor.apply();
        editor.commit();
    }

    public int getOrderStatus() {
        return sharedPreferences.getInt("order_status", 0);
    }

    public void setOrderId(int order_status_id) {
        editor.putInt("order_status_id", order_status_id);
        editor.apply();
        editor.commit();
    }

    public int getOrderId() {
        return sharedPreferences.getInt("order_status_id", 0);
    }

    public void setDeliverAddress(String delivery_address) {
        editor.putString("delivery_address", delivery_address);
        editor.apply();
        editor.commit();
    }

    public String getDeliveryAddress() {
        return sharedPreferences.getString("delivery_address", "");
    }

    public void setCardId(int card_id) {
        editor.putInt("card_id", card_id);
        editor.apply();
        editor.commit();
    }

    public int getCardId() {
        return sharedPreferences.getInt("card_id", 0);
    }

    public void setLanguage(String language) {
        editor.putString("language",language);
        editor.apply();
        editor.commit();
    }
    public String getLanguage(){
        return sharedPreferences.getString("language","");
    }
}