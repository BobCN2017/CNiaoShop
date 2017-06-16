package com.ff.pp.cniao.provinceCityDistrict.model;

/**
 * Created by PP on 2017/4/20.
 */

public class DistrictModel {
    String name;
    String zipCode;

    public DistrictModel() {
        super();

    }

    public DistrictModel(String name, String zipCode) {
        super();
        this.name = name;
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "DistrictModel [name=" + name + ", zipcode=" + zipCode + "]";
    }
}
