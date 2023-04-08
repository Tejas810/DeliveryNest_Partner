package com.example.deliverynestpartner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DeliverysAdapter extends ArrayAdapter<Delivery_POJO> {
    ArrayList<Delivery_POJO> arr=new ArrayList<>();
    public DeliverysAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<Delivery_POJO> objects) {
        super(context, resource, textViewResourceId, objects);
        arr=objects;
    }
    @Override
    public int getCount(){
        return super.getCount();
    }
    @Override
    public View getView(int position,View view, ViewGroup viewGroup){
        view=View.inflate(getContext(),R.layout.delivery_layout,null);

        return view;
    }

}
