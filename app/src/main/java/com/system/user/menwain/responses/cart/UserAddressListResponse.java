package com.system.user.menwain.responses.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserAddressListResponse {
    @SerializedName("addresslist")
    @Expose
    private List<Addresslist> addresslist = null;

    public List<Addresslist> getAddresslist() {
        return addresslist;
    }

    public void setAddresslist(List<Addresslist> addresslist) {
        this.addresslist = addresslist;
    }
    public class Addresslist {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("customer_id")
        @Expose
        private Integer customerId;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("address_line1")
        @Expose
        private String addressLine1;
        @SerializedName("address_line2")
        @Expose
        private String addressLine2;
        @SerializedName("is_favorite")
        @Expose
        private Integer isFavorite;
        @SerializedName("primary")
        @Expose
        private Integer primary;
        @SerializedName("date_created")
        @Expose
        private String dateCreated;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
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

        public String getAddressLine1() {
            return addressLine1;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public String getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public Integer getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(Integer isFavorite) {
            this.isFavorite = isFavorite;
        }

        public Integer getPrimary() {
            return primary;
        }

        public void setPrimary(Integer primary) {
            this.primary = primary;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

    }

}


