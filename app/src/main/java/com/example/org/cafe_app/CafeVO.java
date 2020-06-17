package com.example.org.cafe_app;

public class CafeVO {
    private byte[] image;
    private String title;

    public byte[] getImage() {return this.image;}
    public String getTitle() {return this.title;}

    public CafeVO(byte[] image, String title){
        this.image = image;
        this.title = title;
    }
}
