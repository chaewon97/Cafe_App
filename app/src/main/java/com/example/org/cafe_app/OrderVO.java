package com.example.org.cafe_app;

import android.view.View;

public class OrderVO {
    public String beverage_name;
    public String beverage_price;
    public String beverage_id;
    public String order_id;
    public String user_id;
    public View.OnClickListener onClickListener;

    public OrderVO(String name, String price, String id, String order_id, String user_id) {
        this.beverage_name = name;
        this.beverage_price = price;
        this.beverage_id = id;
        this.order_id = order_id;
        this.user_id = user_id;
    }

    public String getBeverage_name() {return beverage_name;}
    public String getBeverage_price() {return beverage_price;}
    public String getBeverage_id() {return beverage_id;}
    public String getOrder_id() {return order_id;}
    public String getUser_id() {return user_id;}
    public View.OnClickListener getOnClickListener() {return onClickListener;}
    public void setOnClickListener(View.OnClickListener listener){
        onClickListener = listener;
    }
}
