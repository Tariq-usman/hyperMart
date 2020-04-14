package com.system.user.menwain.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailsResponse {
    @SerializedName("Data")
    @Expose
    private Data data;
    @SerializedName("rating")
    @Expose
    private Double rating;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public class Data {

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
        @SerializedName("productpic")
        @Expose
        private List<Productpic> productpic = null;
        @SerializedName("supercategory")
        @Expose
        private Supercategory supercategory;
        @SerializedName("category")
        @Expose
        private Category category;
        @SerializedName("subcategory")
        @Expose
        private Subcategory subcategory;
        @SerializedName("reviewss")
        @Expose
        private List<Reviews> reviewss = null;

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

        public List<Productpic> getProductpic() {
            return productpic;
        }

        public void setProductpic(List<Productpic> productpic) {
            this.productpic = productpic;
        }

        public Supercategory getSupercategory() {
            return supercategory;
        }

        public void setSupercategory(Supercategory supercategory) {
            this.supercategory = supercategory;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Subcategory getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(Subcategory subcategory) {
            this.subcategory = subcategory;
        }

        public List<Reviews> getReviewss() {
            return reviewss;
        }

        public void setReviewss(List<Reviews> reviewss) {
            this.reviewss = reviewss;
        }

        public class Productpic {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("product_id")
            @Expose
            private Integer productId;
            @SerializedName("image_name")
            @Expose
            private String imageName;
            @SerializedName("image_url")
            @Expose
            private String imageUrl;
            @SerializedName("other")
            @Expose
            private String other;
            @SerializedName("created_at")
            @Expose
            private Object createdAt;
            @SerializedName("updated_at")
            @Expose
            private Object updatedAt;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getProductId() {
                return productId;
            }

            public void setProductId(Integer productId) {
                this.productId = productId;
            }

            public String getImageName() {
                return imageName;
            }

            public void setImageName(String imageName) {
                this.imageName = imageName;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getOther() {
                return other;
            }

            public void setOther(String other) {
                this.other = other;
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

        public class Category {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("sup_category_id")
            @Expose
            private Integer supCategoryId;
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

            public Integer getSupCategoryId() {
                return supCategoryId;
            }

            public void setSupCategoryId(Integer supCategoryId) {
                this.supCategoryId = supCategoryId;
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

        public class Reviews {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("product_id")
            @Expose
            private Integer productId;
            @SerializedName("customer_id")
            @Expose
            private Integer customerId;
            @SerializedName("rating")
            @Expose
            private Integer rating;
            @SerializedName("review")
            @Expose
            private String review;
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

            public Integer getProductId() {
                return productId;
            }

            public void setProductId(Integer productId) {
                this.productId = productId;
            }

            public Integer getCustomerId() {
                return customerId;
            }

            public void setCustomerId(Integer customerId) {
                this.customerId = customerId;
            }

            public Integer getRating() {
                return rating;
            }

            public void setRating(Integer rating) {
                this.rating = rating;
            }

            public String getReview() {
                return review;
            }

            public void setReview(String review) {
                this.review = review;
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






