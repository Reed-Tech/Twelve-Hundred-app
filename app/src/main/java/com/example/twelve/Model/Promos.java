package com.example.twelve.Model;

import java.util.ArrayList;

public class Promos {
    private String Category, gender, Pname, Price,Promo_price, date, description, pid, time;
    private ArrayList<String> Imageurllist;

    public Promos() {
    }

    public Promos(String category, String gender, String pname, String price, String promo_price, String date, String description, String pid, String time, ArrayList<String> imageurllist) {
        Category = category;
        this.gender = gender;
        Pname = pname;
        Price = price;
        Promo_price = promo_price;
        this.date = date;
        this.description = description;
        this.pid = pid;
        this.time = time;
        Imageurllist = imageurllist;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPromo_price() {
        return Promo_price;
    }

    public void setPromo_price(String promo_price) {
        Promo_price = promo_price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getImageurllist() {
        return Imageurllist;
    }

    public void setImageurllist(ArrayList<String> imageurllist) {
        Imageurllist = imageurllist;
    }
}
