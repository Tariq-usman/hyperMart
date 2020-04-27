package com.system.user.menwain.responses.more.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsResponse {

        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }


    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("total_price")
        @Expose
        private Integer totalPrice;
        @SerializedName("store_id")
        @Expose
        private Integer storeId;
        @SerializedName("date_time")
        @Expose
        private String dateTime;
        @SerializedName("order_status")
        @Expose
        private String orderStatus;
        @SerializedName("customer_id")
        @Expose
        private Integer customerId;
        @SerializedName("slot_id")
        @Expose
        private Integer slotId;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("preferred_delivery_date")
        @Expose
        private String preferredDeliveryDate;
        @SerializedName("store")
        @Expose
        private Store store;
        @SerializedName("productss")
        @Expose
        private List<Products> productss = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Integer getStoreId() {
            return storeId;
        }

        public void setStoreId(Integer storeId) {
            this.storeId = storeId;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Integer getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
        }

        public Integer getSlotId() {
            return slotId;
        }

        public void setSlotId(Integer slotId) {
            this.slotId = slotId;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public String getPreferredDeliveryDate() {
            return preferredDeliveryDate;
        }

        public void setPreferredDeliveryDate(String preferredDeliveryDate) {
            this.preferredDeliveryDate = preferredDeliveryDate;
        }

        public Store getStore() {
            return store;
        }

        public void setStore(Store store) {
            this.store = store;
        }

        public List<Products> getProductss() {
            return productss;
        }

        public void setProductss(List<Products> productss) {
            this.productss = productss;
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

            public Integer getAverageRating() {
                return averageRating;
            }

            public void setAverageRating(Integer averageRating) {
                this.averageRating = averageRating;
            }

        }
        public class Products {

            @SerializedName("product_id")
            @Expose
            private Integer productId;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("brand")
            @Expose
            private String brand;
            @SerializedName("storeprice")
            @Expose
            private Integer storeprice;
            @SerializedName("pivot")
            @Expose
            private Pivot pivot;

            public Integer getProductId() {
                return productId;
            }

            public void setProductId(Integer productId) {
                this.productId = productId;
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

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public Integer getStoreprice() {
                return storeprice;
            }

            public void setStoreprice(Integer storeprice) {
                this.storeprice = storeprice;
            }

            public Pivot getPivot() {
                return pivot;
            }

            public void setPivot(Pivot pivot) {
                this.pivot = pivot;
            }
            public class Pivot {

                @SerializedName("order_num")
                @Expose
                private Integer orderNum;
                @SerializedName("product_id")
                @Expose
                private Integer productId;
                @SerializedName("quantity")
                @Expose
                private Integer quantity;
                @SerializedName("price")
                @Expose
                private Integer price;
                @SerializedName("discount")
                @Expose
                private Integer discount;
                @SerializedName("created_at")
                @Expose
                private Object createdAt;
                @SerializedName("updated_at")
                @Expose
                private Object updatedAt;

                public Integer getOrderNum() {
                    return orderNum;
                }

                public void setOrderNum(Integer orderNum) {
                    this.orderNum = orderNum;
                }

                public Integer getProductId() {
                    return productId;
                }

                public void setProductId(Integer productId) {
                    this.productId = productId;
                }

                public Integer getQuantity() {
                    return quantity;
                }

                public void setQuantity(Integer quantity) {
                    this.quantity = quantity;
                }

                public Integer getPrice() {
                    return price;
                }

                public void setPrice(Integer price) {
                    this.price = price;
                }

                public Integer getDiscount() {
                    return discount;
                }

                public void setDiscount(Integer discount) {
                    this.discount = discount;
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

            }

        }

    }

}





