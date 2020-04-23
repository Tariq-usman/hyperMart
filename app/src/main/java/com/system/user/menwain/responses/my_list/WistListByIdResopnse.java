package com.system.user.menwain.responses.my_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WistListByIdResopnse {
    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
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
        @SerializedName("pivot")
        @Expose
        private Pivot pivot;

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

        public Pivot getPivot() {
            return pivot;
        }

        public void setPivot(Pivot pivot) {
            this.pivot = pivot;
        }

        public class Pivot {

            @SerializedName("store_id")
            @Expose
            private Integer storeId;
            @SerializedName("product_id")
            @Expose
            private Integer productId;
            @SerializedName("stock")
            @Expose
            private Integer stock;
            @SerializedName("price")
            @Expose
            private Integer price;
            @SerializedName("store_product_url")
            @Expose
            private String storeProductUrl;
            @SerializedName("status")
            @Expose
            private Integer status;
            @SerializedName("other1")
            @Expose
            private String other1;
            @SerializedName("other2")
            @Expose
            private String other2;
            @SerializedName("retailer_product_id")
            @Expose
            private Integer retailerProductId;
            @SerializedName("created_at")
            @Expose
            private Object createdAt;
            @SerializedName("updated_at")
            @Expose
            private Object updatedAt;

            public Integer getStoreId() {
                return storeId;
            }

            public void setStoreId(Integer storeId) {
                this.storeId = storeId;
            }

            public Integer getProductId() {
                return productId;
            }

            public void setProductId(Integer productId) {
                this.productId = productId;
            }

            public Integer getStock() {
                return stock;
            }

            public void setStock(Integer stock) {
                this.stock = stock;
            }

            public Integer getPrice() {
                return price;
            }

            public void setPrice(Integer price) {
                this.price = price;
            }

            public String getStoreProductUrl() {
                return storeProductUrl;
            }

            public void setStoreProductUrl(String storeProductUrl) {
                this.storeProductUrl = storeProductUrl;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getOther1() {
                return other1;
            }

            public void setOther1(String other1) {
                this.other1 = other1;
            }

            public String getOther2() {
                return other2;
            }

            public void setOther2(String other2) {
                this.other2 = other2;
            }

            public Integer getRetailerProductId() {
                return retailerProductId;
            }

            public void setRetailerProductId(Integer retailerProductId) {
                this.retailerProductId = retailerProductId;
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



