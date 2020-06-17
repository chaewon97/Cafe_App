package com.example.org.cafe_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class Cafe_List_Fragment extends Fragment implements CafeListAdapter.OnItemClickListner{
    ViewGroup viewGroup;
    private RecyclerView rcc_cafe;
    private CafeListAdapter mCafeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        viewGroup = (ViewGroup) inflater.inflate(R.layout.cafe_list_fragment, container, false);

        init();
        return viewGroup;
    }

    private void init(){
        rcc_cafe = (RecyclerView) viewGroup.findViewById(R.id.cafe_list);
    }

    @Override
    public void onItemClick(View view, CafeVO cafeVO) {
        Log.e("RecyclerView :: ", cafeVO.toString());
    }
}
