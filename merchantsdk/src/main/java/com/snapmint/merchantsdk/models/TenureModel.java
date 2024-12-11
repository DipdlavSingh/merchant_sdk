package com.snapmint.merchantsdk.models;

import com.google.gson.annotations.SerializedName;

public class TenureModel {
    @SerializedName("tenure")
    private Integer tenure;

    @SerializedName("roi")
    private double roi;

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }
}
