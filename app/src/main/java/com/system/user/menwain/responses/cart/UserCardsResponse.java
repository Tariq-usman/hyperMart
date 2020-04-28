package com.system.user.menwain.responses.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserCardsResponse {

        @SerializedName("cards")
        @Expose
        private Cards cards;

        public Cards getCards() {
            return cards;
        }

        public void setCards(Cards cards) {
            this.cards = cards;
        }

    public class Cards {

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
            @SerializedName("holder_name")
            @Expose
            private String holderName;
            @SerializedName("card_number")
            @Expose
            private String cardNumber;
            @SerializedName("zip_code")
            @Expose
            private String zipCode;
            @SerializedName("cvv")
            @Expose
            private Integer cvv;
            @SerializedName("expiry")
            @Expose
            private String expiry;
            @SerializedName("is_favorite")
            @Expose
            private Integer isFavorite;
            @SerializedName("issuing_company")
            @Expose
            private String issuingCompany;
            @SerializedName("card_bill_address")
            @Expose
            private String cardBillAddress;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;

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

            public String getHolderName() {
                return holderName;
            }

            public void setHolderName(String holderName) {
                this.holderName = holderName;
            }

            public String getCardNumber() {
                return cardNumber;
            }

            public void setCardNumber(String cardNumber) {
                this.cardNumber = cardNumber;
            }

            public String getZipCode() {
                return zipCode;
            }

            public void setZipCode(String zipCode) {
                this.zipCode = zipCode;
            }

            public Integer getCvv() {
                return cvv;
            }

            public void setCvv(Integer cvv) {
                this.cvv = cvv;
            }

            public String getExpiry() {
                return expiry;
            }

            public void setExpiry(String expiry) {
                this.expiry = expiry;
            }

            public Integer getIsFavorite() {
                return isFavorite;
            }

            public void setIsFavorite(Integer isFavorite) {
                this.isFavorite = isFavorite;
            }

            public String getIssuingCompany() {
                return issuingCompany;
            }

            public void setIssuingCompany(String issuingCompany) {
                this.issuingCompany = issuingCompany;
            }

            public String getCardBillAddress() {
                return cardBillAddress;
            }

            public void setCardBillAddress(String cardBillAddress) {
                this.cardBillAddress = cardBillAddress;
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


