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
            @SerializedName("barcode")
            @Expose
            private Integer barcode;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("brand")
            @Expose
            private String brand;
            @SerializedName("lowest_price")
            @Expose
            private Integer lowestPrice;
            @SerializedName("avg_price")
            @Expose
            private Integer avgPrice;
            @SerializedName("highest_price")
            @Expose
            private Integer highestPrice;
            @SerializedName("currency")
            @Expose
            private String currency;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("sub_type")
            @Expose
            private String subType;
            @SerializedName("size")
            @Expose
            private Integer size;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("packed_single")
            @Expose
            private Integer packedSingle;
            @SerializedName("specification")
            @Expose
            private String specification;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("country_of_origin")
            @Expose
            private String countryOfOrigin;
            @SerializedName("category_id")
            @Expose
            private Integer categoryId;
            @SerializedName("sub_category_id")
            @Expose
            private Integer subCategoryId;
            @SerializedName("super_category_id")
            @Expose
            private Integer superCategoryId;
            @SerializedName("created_at")
            @Expose
            private Object createdAt;
            @SerializedName("updated_at")
            @Expose
            private Object updatedAt;
            @SerializedName("deleted_at")
            @Expose
            private Object deletedAt;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getBarcode() {
                return barcode;
            }

            public void setBarcode(Integer barcode) {
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

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public Integer getLowestPrice() {
                return lowestPrice;
            }

            public void setLowestPrice(Integer lowestPrice) {
                this.lowestPrice = lowestPrice;
            }

            public Integer getAvgPrice() {
                return avgPrice;
            }

            public void setAvgPrice(Integer avgPrice) {
                this.avgPrice = avgPrice;
            }

            public Integer getHighestPrice() {
                return highestPrice;
            }

            public void setHighestPrice(Integer highestPrice) {
                this.highestPrice = highestPrice;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSubType() {
                return subType;
            }

            public void setSubType(String subType) {
                this.subType = subType;
            }

            public Integer getSize() {
                return size;
            }

            public void setSize(Integer size) {
                this.size = size;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public Integer getPackedSingle() {
                return packedSingle;
            }

            public void setPackedSingle(Integer packedSingle) {
                this.packedSingle = packedSingle;
            }

            public String getSpecification() {
                return specification;
            }

            public void setSpecification(String specification) {
                this.specification = specification;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCountryOfOrigin() {
                return countryOfOrigin;
            }

            public void setCountryOfOrigin(String countryOfOrigin) {
                this.countryOfOrigin = countryOfOrigin;
            }

            public Integer getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(Integer categoryId) {
                this.categoryId = categoryId;
            }

            public Integer getSubCategoryId() {
                return subCategoryId;
            }

            public void setSubCategoryId(Integer subCategoryId) {
                this.subCategoryId = subCategoryId;
            }

            public Integer getSuperCategoryId() {
                return superCategoryId;
            }

            public void setSuperCategoryId(Integer superCategoryId) {
                this.superCategoryId = superCategoryId;
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

        }

    }

}



