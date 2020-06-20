package com.example.org.cafe_app;

public class ItemVO {
    private String title;
    private String id;
    private int thumb_nail;
    private int price;


    public int getThumb_nail() {return this.thumb_nail;}
    public String getTitle() {return this.title;}
    public String getId() {return this.id;}
    public int getPrice() {return this.price;}

    public ItemVO(int thumb_nail, String title, String id, int price){
        this.thumb_nail = thumb_nail;
        this.title = title;
        this.id = id;
        this.price = price;
    }
}
