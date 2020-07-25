package com.system.user.menwain.responses.more.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleveredOrdersResponse {

    @SerializedName("allorders")
    @Expose
    private Allorders allorders;

    public Allorders getAllorders() {
        return allorders;
    }

    public void setAllorders(Allorders allorders) {
        this.allorders = allorders;
    }

    public class Allorders {

        @SerializedName("current_page")
        @Expose
        private Integer currentPage;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;
        @SerializedName("first_page_url")
        @Expose
        private String firstPageUrl;
        @SerializedName("from")
        @Expose
        private Integer from;
        @SerializedName("last_page")
        @Expose
        private Integer lastPage;
        @SerializedName("last_page_url")
        @Expose
        private String lastPageUrl;
        @SerializedName("next_page_url")
        @Expose
        private Object nextPageUrl;
        @SerializedName("path")
        @Expose
        private String path;
        @SerializedName("per_page")
        @Expose
        private Integer perPage;
        @SerializedName("prev_page_url")
        @Expose
        private Object prevPageUrl;
        @SerializedName("to")
        @Expose
        private Integer to;
        @SerializedName("total")
        @Expose
        private Integer total;

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public String getFirstPageUrl() {
            return firstPageUrl;
        }

        public void setFirstPageUrl(String firstPageUrl) {
            this.firstPageUrl = firstPageUrl;
        }

        public Integer getFrom() {
            return from;
        }

        public void setFrom(Integer from) {
            this.from = from;
        }

        public Integer getLastPage() {
            return lastPage;
        }

        public void setLastPage(Integer lastPage) {
            this.lastPage = lastPage;
        }

        public String getLastPageUrl() {
            return lastPageUrl;
        }

        public void setLastPageUrl(String lastPageUrl) {
            this.lastPageUrl = lastPageUrl;
        }

        public Object getNextPageUrl() {
            return nextPageUrl;
        }

        public void setNextPageUrl(Object nextPageUrl) {
            this.nextPageUrl = nextPageUrl;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getPerPage() {
            return perPage;
        }

        public void setPerPage(Integer perPage) {
            this.perPage = perPage;
        }

        public Object getPrevPageUrl() {
            return prevPageUrl;
        }

        public void setPrevPageUrl(Object prevPageUrl) {
            this.prevPageUrl = prevPageUrl;
        }

        public Integer getTo() {
            return to;
        }

        public void setTo(Integer to) {
            this.to = to;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public class Datum {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("customer_id")
            @Expose
            private Integer customerId;
            @SerializedName("total_price")
            @Expose
            private Integer totalPrice;
            @SerializedName("discount")
            @Expose
            private Integer discount;
            @SerializedName("slot_id")
            @Expose
            private Integer slotId;
            @SerializedName("preferred_delivery_date")
            @Expose
            private String preferredDeliveryDate;
            @SerializedName("payment_method_id")
            @Expose
            private Integer paymentMethodId;
            @SerializedName("store_id")
            @Expose
            private Integer storeId;
            @SerializedName("address_id")
            @Expose
            private Integer addressId;
            @SerializedName("promotion_id")
            @Expose
            private Integer promotionId;
            @SerializedName("shipping_method_id")
            @Expose
            private Integer shippingMethodId;
            @SerializedName("shipping_cost")
            @Expose
            private Integer shippingCost;
            @SerializedName("secret_code")
            @Expose
            private Integer secretCode;
            @SerializedName("date_time")
            @Expose
            private String dateTime;
            @SerializedName("delivery_man_id")
            @Expose
            private Integer deliveryManId;
            @SerializedName("delivery_note")
            @Expose
            private Object deliveryNote;
            @SerializedName("order_status")
            @Expose
            private String orderStatus;
            @SerializedName("order_dispatch_id")
            @Expose
            private Integer orderDispatchId;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;
            @SerializedName("deleted_at")
            @Expose
            private Object deletedAt;
            @SerializedName("store")
            @Expose
            private Store store;

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

            public Integer getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(Integer totalPrice) {
                this.totalPrice = totalPrice;
            }

            public Integer getDiscount() {
                return discount;
            }

            public void setDiscount(Integer discount) {
                this.discount = discount;
            }

            public Integer getSlotId() {
                return slotId;
            }

            public void setSlotId(Integer slotId) {
                this.slotId = slotId;
            }

            public String getPreferredDeliveryDate() {
                return preferredDeliveryDate;
            }

            public void setPreferredDeliveryDate(String preferredDeliveryDate) {
                this.preferredDeliveryDate = preferredDeliveryDate;
            }

            public Integer getPaymentMethodId() {
                return paymentMethodId;
            }

            public void setPaymentMethodId(Integer paymentMethodId) {
                this.paymentMethodId = paymentMethodId;
            }

            public Integer getStoreId() {
                return storeId;
            }

            public void setStoreId(Integer storeId) {
                this.storeId = storeId;
            }

            public Integer getAddressId() {
                return addressId;
            }

            public void setAddressId(Integer addressId) {
                this.addressId = addressId;
            }

            public Integer getPromotionId() {
                return promotionId;
            }

            public void setPromotionId(Integer promotionId) {
                this.promotionId = promotionId;
            }

            public Integer getShippingMethodId() {
                return shippingMethodId;
            }

            public void setShippingMethodId(Integer shippingMethodId) {
                this.shippingMethodId = shippingMethodId;
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

            public Integer getDeliveryManId() {
                return deliveryManId;
            }

            public void setDeliveryManId(Integer deliveryManId) {
                this.deliveryManId = deliveryManId;
            }

            public Object getDeliveryNote() {
                return deliveryNote;
            }

            public void setDeliveryNote(Object deliveryNote) {
                this.deliveryNote = deliveryNote;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public Integer getOrderDispatchId() {
                return orderDispatchId;
            }

            public void setOrderDispatchId(Integer orderDispatchId) {
                this.orderDispatchId = orderDispatchId;
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

            public Store getStore() {
                return store;
            }

            public void setStore(Store store) {
                this.store = store;
            }

            public class Store {

                @SerializedName("id")
                @Expose
                private Integer id;
                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("image")
                @Expose
                private String image;
                @SerializedName("mobile")
                @Expose
                private String mobile;
                @SerializedName("secret_code")
                @Expose
                private Integer secretCode;
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

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public Integer getSecretCode() {
                    return secretCode;
                }

                public void setSecretCode(Integer secretCode) {
                    this.secretCode = secretCode;
                }

                public Integer getAverageRating() {
                    return averageRating;
                }

                public void setAverageRating(Integer averageRating) {
                    this.averageRating = averageRating;
                }

            }

        }

    }

}



