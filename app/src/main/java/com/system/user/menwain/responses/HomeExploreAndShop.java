package com.system.user.menwain.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeExploreAndShop {

    @SerializedName("Dymmy 1")
    @Expose
    private List<Dymmy1> dymmy1 = null;
    @SerializedName("Dummy 2")
    @Expose
    private List<Dummy2> dummy2 = null;
    @SerializedName("Dummy 3")
    @Expose
    private List<Dummy3> dummy3 = null;

    public List<Dymmy1> getDymmy1() {
        return dymmy1;
    }

    public void setDymmy1(List<Dymmy1> dymmy1) {
        this.dymmy1 = dymmy1;
    }

    public List<Dummy2> getDummy2() {
        return dummy2;
    }

    public void setDummy2(List<Dummy2> dummy2) {
        this.dummy2 = dummy2;
    }

    public List<Dummy3> getDummy3() {
        return dummy3;
    }

    public void setDummy3(List<Dummy3> dummy3) {
        this.dummy3 = dummy3;
    }

    public class Dymmy1 {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("avg_price")
        @Expose
        private Integer avgPrice;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAvgPrice() {
            return avgPrice;
        }

        public void setAvgPrice(Integer avgPrice) {
            this.avgPrice = avgPrice;
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

    }

    public class Dummy2 {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("avg_price")
        @Expose
        private Integer avgPrice;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAvgPrice() {
            return avgPrice;
        }

        public void setAvgPrice(Integer avgPrice) {
            this.avgPrice = avgPrice;
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

    }

    public class Dummy3 {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("avg_price")
        @Expose
        private Integer avgPrice;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAvgPrice() {
            return avgPrice;
        }

        public void setAvgPrice(Integer avgPrice) {
            this.avgPrice = avgPrice;
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

    }

}




