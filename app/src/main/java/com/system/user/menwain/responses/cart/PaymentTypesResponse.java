package com.system.user.menwain.responses.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentTypesResponse {
        @SerializedName("data_pay_now")
        @Expose
        private List<DataPayNow> dataPayNow = null;
        @SerializedName("data_pay_later")
        @Expose
        private List<DataPayLater> dataPayLater = null;
        @SerializedName("addresslist")
        @Expose
        private List<Addresslist> addresslist = null;

        public List<DataPayNow> getDataPayNow() {
            return dataPayNow;
        }

        public void setDataPayNow(List<DataPayNow> dataPayNow) {
            this.dataPayNow = dataPayNow;
        }

        public List<DataPayLater> getDataPayLater() {
            return dataPayLater;
        }

        public void setDataPayLater(List<DataPayLater> dataPayLater) {
            this.dataPayLater = dataPayLater;
        }

        public List<Addresslist> getAddresslist() {
            return addresslist;
        }

        public void setAddresslist(List<Addresslist> addresslist) {
            this.addresslist = addresslist;
        }

    public class DataPayNow {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("discription")
        @Expose
        private String discription;
        @SerializedName("paymenttypes")
        @Expose
        private List<Paymenttype> paymenttypes = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public List<Paymenttype> getPaymenttypes() {
            return paymenttypes;
        }

        public void setPaymenttypes(List<Paymenttype> paymenttypes) {
            this.paymenttypes = paymenttypes;
        }
        public class Paymenttype {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("paymentmethod_id")
            @Expose
            private Integer paymentmethodId;
            @SerializedName("payment_method_description")
            @Expose
            private String paymentMethodDescription;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getPaymentmethodId() {
                return paymentmethodId;
            }

            public void setPaymentmethodId(Integer paymentmethodId) {
                this.paymentmethodId = paymentmethodId;
            }

            public String getPaymentMethodDescription() {
                return paymentMethodDescription;
            }

            public void setPaymentMethodDescription(String paymentMethodDescription) {
                this.paymentMethodDescription = paymentMethodDescription;
            }

        }

    }
    public class DataPayLater {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("discription")
        @Expose
        private String discription;
        @SerializedName("paymenttypes")
        @Expose
        private List<Paymenttype_> paymenttypes = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public List<Paymenttype_> getPaymenttypes() {
            return paymenttypes;
        }

        public void setPaymenttypes(List<Paymenttype_> paymenttypes) {
            this.paymenttypes = paymenttypes;
        }
        public class Paymenttype_ {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("paymentmethod_id")
            @Expose
            private Integer paymentmethodId;
            @SerializedName("payment_method_description")
            @Expose
            private String paymentMethodDescription;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getPaymentmethodId() {
                return paymentmethodId;
            }

            public void setPaymentmethodId(Integer paymentmethodId) {
                this.paymentmethodId = paymentmethodId;
            }

            public String getPaymentMethodDescription() {
                return paymentMethodDescription;
            }

            public void setPaymentMethodDescription(String paymentMethodDescription) {
                this.paymentMethodDescription = paymentMethodDescription;
            }

        }

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
        private Object addressLine2;
        @SerializedName("is_favorite")
        @Expose
        private Object isFavorite;
        @SerializedName("primary")
        @Expose
        private Object primary;
        @SerializedName("date_created")
        @Expose
        private Object dateCreated;
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

        public Object getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(Object addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public Object getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(Object isFavorite) {
            this.isFavorite = isFavorite;
        }

        public Object getPrimary() {
            return primary;
        }

        public void setPrimary(Object primary) {
            this.primary = primary;
        }

        public Object getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(Object dateCreated) {
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






