package com.system.user.menwain.responses.more.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllOrdersResponse {

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
        private String nextPageUrl;
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

        public String getNextPageUrl() {
            return nextPageUrl;
        }

        public void setNextPageUrl(String nextPageUrl) {
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
            @SerializedName("total_price")
            @Expose
            private Integer totalPrice;
            @SerializedName("date_time")
            @Expose
            private String dateTime;
            @SerializedName("order_status")
            @Expose
            private String orderStatus;
            @SerializedName("store_id")
            @Expose
            private Integer storeId;
            @SerializedName("secret_code")
            @Expose
            private Integer secretCode;
            @SerializedName("preferred_delivery_date")
            @Expose
            private String preferredDeliveryDate;
            @SerializedName("store")
            @Expose
            private Store store;

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

            public Integer getStoreId() {
                return storeId;
            }

            public void setStoreId(Integer storeId) {
                this.storeId = storeId;
            }

            public Integer getSecretCode() {
                return secretCode;
            }

            public void setSecretCode(Integer secretCode) {
                this.secretCode = secretCode;
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