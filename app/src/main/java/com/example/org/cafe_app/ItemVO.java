package com.example.org.cafe_app;

public class ItemVO {
    private String title;
    private String id;
    private int thumb_nail;
    private int price;
    private int type;
    private String popul;


    public int getThumb_nail() {return this.thumb_nail;}
    public String getTitle() {return this.title;}
    public String getId() {return this.id;}
    public int getPrice() {return this.price;}
    public int getType() {return this.type;}
    public String getPopul() {return this.popul;}

    public ItemVO(int thumb_nail, String title, String id, int price, int type, String popul){
        this.thumb_nail = thumb_nail;
        this.title = title;
        this.id = id;
        this.price = price;
        this.type = type;
        this.popul = popul;
    }
}
