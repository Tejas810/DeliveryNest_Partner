package com.example.deliverynestpartner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DeliverysAdapter extends ArrayAdapter<Delivery_POJO> {
    ArrayList<Delivery_POJO> arr=new ArrayList<>(50);
    public DeliverysAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Delivery_POJO> objects) {
        super(context, resource, objects);
        arr=objects;
    }
    @Override
    public int getCount(){
        return super.getCount();
    }
    @Override
    public View getView(int position,View view, ViewGroup viewGroup){
        view=View.inflate(getContext(),R.layout.delivery_layout,null);
        ((TextView)view.findViewById(R.id.OrderId)).setText(arr.get(position).getOrderid());
        ((TextView)view.findViewById(R.id.ReceiverAddress)).setText(arr.get(position).getReceiveraddress());
        ((TextView)view.findViewById(R.id.Price)).setText(arr.get(position).getPrice());
        return view;
    }
}