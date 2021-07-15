package com.example.twelve.Model;

public class Stores {
    private String storename, storemail, storepassword, storephone;

    public Stores() {
    }

    public Stores(String storename, String storemail, String storepassword, String storephone) {
        this.storename = storename;
        this.storemail = storemail;
        this.storepassword = storepassword;
        this.storephone = storephone;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getStoremail() {
        return storemail;
    }

    public void setStoremail(String storemail) {
        this.storemail = storemail;
    }

    public String getStorepassword() {
        return storepassword;
    }

    public void setStorepassword(String storepassword) {
        this.storepassword = storepassword;
    }

    public String getStorephone() {
        return storephone;
    }

    public void setStorephone(String storephone) {
        this.storephone = storephone;
    }
}
