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
        private String deliveryNote;
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
        @SerializedName("productss")
        @Expose
        private List<Products> productss = null;

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

        public String getDeliveryNote() {
            return deliveryNote;
        }

        public void setDeliveryNote(String deliveryNote) {
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
            @SerializedName("mobile")
            @Expose
            private String mobile;
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

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
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
                private String createdAt;
                @SerializedName("updated_at")
                @Expose
                private String updatedAt;

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

            }

        }

    }

    }





