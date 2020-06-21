package com.example.org.cafe_app;

import android.graphics.drawable.Drawable;

public class CafeVO {
    private byte[] image;
    private String title;
    private String id;
    private int thumb_nail;


    public byte[] getImage() {return this.image;}
    public int getThumb_nail() {return this.thumb_nail;}
    public String getTitle() {return this.title;}
    public String getId() {return this.id;}


    public CafeVO(byte[] image, String title){
        this.image = image;
        this.title = title;
    }

    public CafeVO(int thumb_nail, String title,String id){
        this.thumb_nail = thumb_nail;
        this.title = title;
        this.id = id;
    }
}
