package com.ff.pp.cniao.provinceCityDistrict;

import com.ff.pp.cniao.provinceCityDistrict.model.CityModel;
import com.ff.pp.cniao.provinceCityDistrict.model.DistrictModel;
import com.ff.pp.cniao.provinceCityDistrict.model.ProvinceModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PP on 2017/4/20.
 */

public class XmlParserHandler extends DefaultHandler{

    private List<ProvinceModel> provinceList=new ArrayList<>();
    private ProvinceModel province=new ProvinceModel();
    private CityModel city=new CityModel();
    private DistrictModel district=new DistrictModel();

    public XmlParserHandler() {
    }

    public List<ProvinceModel> getProvinceList() {
        return provinceList;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName){
            case "province":
                province=new ProvinceModel();
                province.setName(attributes.getValue(0));
                province.setCiteList(new ArrayList<CityModel>());
                break;
            case "city":
                city=new CityModel();
                city.setName(attributes.getValue(0));
                city.setDistrictList(new ArrayList<DistrictModel>());
                break;
            case "district":
                district=new DistrictModel();
                district.setName(attributes.getValue(0));
                district.setZipCode(attributes.getValue(1));
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName){
            case "province":
                provinceList.add(province);
                break;
            case "city":
                province.getCiteList().add(city);
                break;
            case "district":
                city.getDistrictList().add(district);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }
}
