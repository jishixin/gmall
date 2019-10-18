package com.jishixin.gmall.pojo;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/*
  User: 晨梦意志
  Date: 2019/9/18
  Time: 13:32
  Notes:
*/
public class PmsSearchSkuInfo implements Serializable {

    @Id
    private String id;

    private String skuName;

    private String skuDesc;

    private String catalog3Id;

    private BigDecimal price;

    private String skuDefaultImg;

    private double hostScore;

    private String productId;

    private List<PmsSkuAttrValue> skuAttrValueList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
    }

    public double getHostScore() {
        return hostScore;
    }

    public void setHostScore(double hostScore) {
        this.hostScore = hostScore;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<PmsSkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<PmsSkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }

    @Override
    public String toString() {
        return "PmsSearchSkuInfo{" +
                "id='" + id + '\'' +
                ", skuName='" + skuName + '\'' +
                ", skuDesc='" + skuDesc + '\'' +
                ", catalog3Id='" + catalog3Id + '\'' +
                ", price=" + price +
                ", skuDefaultImg='" + skuDefaultImg + '\'' +
                ", hostScore=" + hostScore +
                ", productId='" + productId + '\'' +
                ", skuAttrValueList=" + skuAttrValueList +
                '}';
    }
}
