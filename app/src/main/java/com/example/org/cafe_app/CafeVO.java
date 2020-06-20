package com.example.org.cafe_app;

import android.graphics.drawable.Drawable;

public class CafeVO {
    private byte[] image;
    private String title;
    private int thumb_nail;


    public byte[] getImage() {return this.image;}
    public int getThumb_nail() {return this.thumb_nail;}
    public String getTitle() {return this.title;}

    public CafeVO(byte[] image, String title){
        this.image = image;
        this.title = title;
    }

    public CafeVO(int thumb_nail, String title){
        this.thumb_nail = thumb_nail;
        this.title = title;
    }
}
