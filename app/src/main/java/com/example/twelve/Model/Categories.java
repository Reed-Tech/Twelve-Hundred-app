package com.example.twelve.Model;

public class Categories {
    private String name, description, gender, category;
    private int photo;

    public Categories(){
    }

    public Categories(String name, String description, String gender, String category, int photo) {
        this.name = name;
        this.description = description;
        this.gender = gender;
        this.category = category;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
