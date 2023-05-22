package com.example.linterobert331;

public class ProfileImage {

    private String email;
    private String imageUrl;

    public ProfileImage(){

    }

    public ProfileImage(String email, String imageUrl){
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
}
