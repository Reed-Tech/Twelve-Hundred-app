package com.example.twelve.Model;

public class Reviews {

    private String username, rev_text, pimg_url, userfullname, useremail;
    private String star;

    public Reviews() {
    }

    public Reviews( String username, String rev_text, String pimg_url, String userfullname, String useremail, String star) {

        this.username = username;
        this.rev_text = rev_text;
        this.pimg_url = pimg_url;
        this.userfullname = userfullname;
        this.useremail = useremail;
        this.star = star;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRev_text() {
        return rev_text;
    }

    public void setRev_text(String rev_text) {
        this.rev_text = rev_text;
    }

    public String getPimg_url() {
        return pimg_url;
    }

    public void setPimg_url(String pimg_url) {
        this.pimg_url = pimg_url;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
