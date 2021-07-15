package com.example.twelve.Model;

public class Notifications {
    private String message;
    private String title;
    private String time;
    private String ref_number;

    public Notifications() {
    }

    public Notifications(String message, String title, String time, String ref_number) {
        this.message = message;
        this.title = title;
        this.time = time;
        this.ref_number = ref_number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRef_number() {
        return ref_number;
    }

    public void setRef_number(String ref_number) {
        this.ref_number = ref_number;
    }
}
