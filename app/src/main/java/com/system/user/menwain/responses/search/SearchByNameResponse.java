package com.system.user.menwain.responses.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchByNameResponse {
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
            @SerializedName("barcode")
            @Expose
            private Object barcode;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("arabic_name")
            @Expose
            private Object arabicName;
            @SerializedName("arabic_discription")
            @Expose
            private Object arabicDiscription;
            @SerializedName("brand")
            @Expose
            private String brand;
            @SerializedName("lowest_price")
            @Expose
            private Object lowestPrice;
            @SerializedName("avg_price")
            @Expose
            private Object avgPrice;
            @SerializedName("highest_price")
            @Expose
            private Object highestPrice;
            @SerializedName("currency")
            @Expose
            private String currency;
            @SerializedName("type")
            @Expose
            private Object type;
            @SerializedName("sub_type")
            @Expose
            private Object subType;
            @SerializedName("size")
            @Expose
            private Object size;
            @SerializedName("image")
            @Expose
            private Object image;
            @SerializedName("packed_single")
            @Expose
            private Object packedSingle;
            @SerializedName("specification")
            @Expose
            private Object specification;
            @SerializedName("status")
            @Expose
            private Object status;
            @SerializedName("country_of_origin")
            @Expose
            private Object countryOfOrigin;
            @SerializedName("category_id")
            @Expose
            private Object categoryId;
            @SerializedName("sub_category_id")
            @Expose
            private Object subCategoryId;
            @SerializedName("super_category_id")
            @Expose
            private Object superCategoryId;
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

            public Object getBarcode() {
                return barcode;
            }

            public void setBarcode(Object barcode) {
                this.barcode = barcode;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Object getArabicName() {
                return arabicName;
            }

            public void setArabicName(Object arabicName) {
                this.arabicName = arabicName;
            }

            public Object getArabicDiscription() {
                return arabicDiscription;
            }

            public void setArabicDiscription(Object arabicDiscription) {
                this.arabicDiscription = arabicDiscription;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public Object getLowestPrice() {
                return lowestPrice;
            }

            public void setLowestPrice(Object lowestPrice) {
                this.lowestPrice = lowestPrice;
            }

            public Object getAvgPrice() {
                return avgPrice;
            }

            public void setAvgPrice(Object avgPrice) {
                this.avgPrice = avgPrice;
            }

            public Object getHighestPrice() {
                return highestPrice;
            }

            public void setHighestPrice(Object highestPrice) {
                this.highestPrice = highestPrice;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public Object getSubType() {
                return subType;
            }

            public void setSubType(Object subType) {
                this.subType = subType;
            }

            public Object getSize() {
                return size;
            }

            public void setSize(Object size) {
                this.size = size;
            }

            public Object getImage() {
                return image;
            }

            public void setImage(Object image) {
                this.image = image;
            }

            public Object getPackedSingle() {
                return packedSingle;
            }

            public void setPackedSingle(Object packedSingle) {
                this.packedSingle = packedSingle;
            }

            public Object getSpecification() {
                return specification;
            }

            public void setSpecification(Object specification) {
                this.specification = specification;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getCountryOfOrigin() {
                return countryOfOrigin;
            }

            public void setCountryOfOrigin(Object countryOfOrigin) {
                this.countryOfOrigin = countryOfOrigin;
            }

            public Object getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(Object categoryId) {
                this.categoryId = categoryId;
            }

            public Object getSubCategoryId() {
                return subCategoryId;
            }

            public void setSubCategoryId(Object subCategoryId) {
                this.subCategoryId = subCategoryId;
            }

            public Object getSuperCategoryId() {
                return superCategoryId;
            }

            public void setSuperCategoryId(Object superCategoryId) {
                this.superCategoryId = superCategoryId;
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

}


