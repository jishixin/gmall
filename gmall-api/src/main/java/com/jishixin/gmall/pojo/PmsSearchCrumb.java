package com.jishixin.gmall.pojo;

/*
  User: 晨梦意志
  Date: 2019/9/19
  Time: 15:00
  Notes:面包屑属性
*/
public class PmsSearchCrumb {

    private String valueId;

    private String valueName;

    private String urlParam;

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    @Override
    public String toString() {
        return "PmsSearchCrumb{" +
                "valueId='" + valueId + '\'' +
                ", valueName='" + valueName + '\'' +
                ", urlParam='" + urlParam + '\'' +
                '}';
    }
}
