package com.system.user.menwain.responses.more.stores;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SelectedStoreResponse {
    @SerializedName("store")
    @Expose
    private Store store;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("product")
    @Expose
    private Product product;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public class Store {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("average-rating")
        @Expose
        private Integer averageRating;

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

        public Integer getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(Integer averageRating) {
            this.averageRating = averageRating;
        }

    }

    public class Category {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("picture")
        @Expose
        private String picture;

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

    }

    public class Product {

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
            @SerializedName("products")
            @Expose
            private List<Product_> products = null;

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

            public List<Product_> getProducts() {
                return products;
            }

            public void setProducts(List<Product_> products) {
                this.products = products;
            }

            public class Product_ {

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

    }

}







