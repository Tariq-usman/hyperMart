package com.system.user.menwain.responses.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExploreSellAllResponse {

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
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("category_id")
        @Expose
        private Integer categoryId;
        @SerializedName("sub_category_id")
        @Expose
        private Integer subCategoryId;
        @SerializedName("super_category_id")
        @Expose
        private Integer superCategoryId;
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
        @SerializedName("supercategory")
        @Expose
        private Supercategory supercategory;
        @SerializedName("category")
        @Expose
        private Object category;
        @SerializedName("subcategory")
        @Expose
        private Subcategory subcategory;

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

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public Supercategory getSupercategory() {
            return supercategory;
        }

        public void setSupercategory(Supercategory supercategory) {
            this.supercategory = supercategory;
        }

        public Object getCategory() {
            return category;
        }

        public void setCategory(Object category) {
            this.category = category;
        }

        public Subcategory getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(Subcategory subcategory) {
            this.subcategory = subcategory;
        }

        public class Subcategory {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("picture")
            @Expose
            private String picture;
            @SerializedName("lowest_price")
            @Expose
            private Integer lowestPrice;
            @SerializedName("highest_price")
            @Expose
            private Integer highestPrice;
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public Integer getLowestPrice() {
                return lowestPrice;
            }

            public void setLowestPrice(Integer lowestPrice) {
                this.lowestPrice = lowestPrice;
            }

            public Integer getHighestPrice() {
                return highestPrice;
            }

            public void setHighestPrice(Integer highestPrice) {
                this.highestPrice = highestPrice;
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

        public class Supercategory {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("picture")
            @Expose
            private String picture;
            @SerializedName("lowest_price")
            @Expose
            private Integer lowestPrice;
            @SerializedName("highest_price")
            @Expose
            private Integer highestPrice;
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public Integer getLowestPrice() {
                return lowestPrice;
            }

            public void setLowestPrice(Integer lowestPrice) {
                this.lowestPrice = lowestPrice;
            }

            public Integer getHighestPrice() {
                return highestPrice;
            }

            public void setHighestPrice(Integer highestPrice) {
                this.highestPrice = highestPrice;
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



