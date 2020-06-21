package com.example.org.cafe_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OrderListAdapter extends BaseAdapter{

    LayoutInflater inflater = null;
    private ArrayList<OrderVO> order_list = null;
    int item_layout;

    public OrderListAdapter(ArrayList<OrderVO>order_list, int item_layout){
        this.order_list = order_list;
        this.item_layout = item_layout;

    }

    @Override
    public int getCount() {
        return order_list.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if(view == null){
            final Context context = viewGroup.getContext();
            if(inflater == null){
                inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            view = inflater.inflate(R.layout.order_item, viewGroup, false);
        }

        TextView beverage_name = (TextView)view.findViewById(R.id.beverage_name);
        TextView beverage_price = (TextView)view.findViewById(R.id.beverage_price);
        Button order_list_cancel = (Button)view.findViewById(R.id.btn_order_cancel);

        beverage_name.setText(order_list.get(position).getBeverage_name());
        beverage_price.setText(order_list.get(position).getBeverage_price());
        order_list_cancel.setOnClickListener(order_list.get(position).onClickListener);

        return view;
    }
}

