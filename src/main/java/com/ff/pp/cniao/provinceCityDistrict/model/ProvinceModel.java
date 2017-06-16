package com.ff.pp.cniao.provinceCityDistrict.model;

import java.util.List;

/**
 * Created by PP on 2017/4/20.
 */

public class ProvinceModel {
    private String name;
    private List<CityModel> citeList;

    public ProvinceModel() {
        super();

    }

    public ProvinceModel(String name, List<CityModel> citeList) {
        super();
        this.name = name;
        this.citeList = citeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityModel> getCiteList() {
        return citeList;
    }

    public void setCiteList(List<CityModel> citeList) {
        this.citeList = citeList;
    }

    @Override
    public String toString() {
        return name;
    }
}
