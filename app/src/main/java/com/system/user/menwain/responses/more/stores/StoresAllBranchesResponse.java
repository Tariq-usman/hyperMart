package com.system.user.menwain.responses.more.stores;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoresAllBranchesResponse {
    @SerializedName("storelist")
    @Expose
    private List<Storelist> storelist = null;

    public List<Storelist> getStorelist() {
        return storelist;
    }

    public void setStorelist(List<Storelist> storelist) {
        this.storelist = storelist;
    }

    public class Storelist {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("active_status")
        @Expose
        private String activeStatus;
        @SerializedName("parent_store_id")
        @Expose
        private Integer parentStoreId;
        @SerializedName("commercial_reg")
        @Expose
        private Integer commercialReg;
        @SerializedName("vat")
        @Expose
        private Integer vat;
        @SerializedName("shipping_cost")
        @Expose
        private Integer shippingCost;
        @SerializedName("secret_code")
        @Expose
        private Integer secretCode;
        @SerializedName("date_time")
        @Expose
        private String dateTime;
        @SerializedName("store_type")
        @Expose
        private String storeType;
        @SerializedName("details")
        @Expose
        private String details;
        @SerializedName("shipping_cost_method")
        @Expose
        private String shippingCostMethod;
        @SerializedName("cash_on_delivery")
        @Expose
        private Integer cashOnDelivery;
        @SerializedName("pay_n_delivery")
        @Expose
        private Integer payNDelivery;
        @SerializedName("pay_n_pickup")
        @Expose
        private Integer payNPickup;
        @SerializedName("has_delivery")
        @Expose
        private Integer hasDelivery;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("average-rating")
        @Expose
        private Integer averageRating;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(String activeStatus) {
            this.activeStatus = activeStatus;
        }

        public Integer getParentStoreId() {
            return parentStoreId;
        }

        public void setParentStoreId(Integer parentStoreId) {
            this.parentStoreId = parentStoreId;
        }

        public Integer getCommercialReg() {
            return commercialReg;
        }

        public void setCommercialReg(Integer commercialReg) {
            this.commercialReg = commercialReg;
        }

        public Integer getVat() {
            return vat;
        }

        public void setVat(Integer vat) {
            this.vat = vat;
        }

        public Integer getShippingCost() {
            return shippingCost;
        }

        public void setShippingCost(Integer shippingCost) {
            this.shippingCost = shippingCost;
        }

        public Integer getSecretCode() {
            return secretCode;
        }

        public void setSecretCode(Integer secretCode) {
            this.secretCode = secretCode;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getStoreType() {
            return storeType;
        }

        public void setStoreType(String storeType) {
            this.storeType = storeType;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getShippingCostMethod() {
            return shippingCostMethod;
        }

        public void setShippingCostMethod(String shippingCostMethod) {
            this.shippingCostMethod = shippingCostMethod;
        }

        public Integer getCashOnDelivery() {
            return cashOnDelivery;
        }

        public void setCashOnDelivery(Integer cashOnDelivery) {
            this.cashOnDelivery = cashOnDelivery;
        }

        public Integer getPayNDelivery() {
            return payNDelivery;
        }

        public void setPayNDelivery(Integer payNDelivery) {
            this.payNDelivery = payNDelivery;
        }

        public Integer getPayNPickup() {
            return payNPickup;
        }

        public void setPayNPickup(Integer payNPickup) {
            this.payNPickup = payNPickup;
        }

        public Integer getHasDelivery() {
            return hasDelivery;
        }

        public void setHasDelivery(Integer hasDelivery) {
            this.hasDelivery = hasDelivery;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public Integer getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(Integer averageRating) {
            this.averageRating = averageRating;
        }

    }
}
