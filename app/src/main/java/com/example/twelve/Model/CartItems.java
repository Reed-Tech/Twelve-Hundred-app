package com.example.twelve.Model;

import java.util.List;

public class CartItems {
    String date, discount, pid, price, tprice, pricenum, productname, quantity, time;
    List<String> images;

    public CartItems() {
    }

    public CartItems(String date, String discount, String pid, String price, String tprice, String pricenum, String productname, String quantity, String time, List<String> images) {
        this.date = date;
        this.discount = discount;
        this.pid = pid;
        this.price = price;
        this.tprice = tprice;
        this.pricenum = pricenum;
        this.productname = productname;
        this.quantity = quantity;
        this.time = time;
        this.images = images;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }

    public String getPricenum() {
        return pricenum;
    }

    public void setPricenum(String pricenum) {
        this.pricenum = pricenum;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
