package com.example.deliverynestpartner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ViewOrder extends Fragment {
    private LoadingDialog aLodingDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_view_order, container, false);

        aLodingDialog = new LoadingDialog(getContext());
        Bundle bundle=getArguments();
        ((TextView)v.findViewById(R.id.OrderId)).append(bundle.get("OrderId").toString());
        ((TextView)v.findViewById(R.id.Status)).append(bundle.get("Status").toString());
        ((TextView)v.findViewById(R.id.AssignedTo)).append(bundle.get("AssignedTo").toString());
        ((TextView)v.findViewById(R.id.BookOption)).append(bundle.get("BookOption").toString());
        ((TextView)v.findViewById(R.id.ItemNameToSend)).append(bundle.get("ItemNameToSend").toString());
        ((TextView)v.findViewById(R.id.NotifyBySmsOption)).append(bundle.get("NotifyPersonOption").toString());
        ((TextView)v.findViewById(R.id.OrderDate)).append(bundle.get("OrderDate").toString());
        ((TextView)v.findViewById(R.id.OrderWeight)).append(bundle.get("OrderWeight").toString());
        ((TextView)v.findViewById(R.id.SenderAddress)).append(bundle.get("PickUpAddress").toString());
        ((TextView)v.findViewById(R.id.BagPrefered)).append(bundle.get("PreferBagOption").toString());
        ((TextView)v.findViewById(R.id.ReceiverAddress)).append(bundle.get("ReceiverAddress").toString());
        ((TextView)v.findViewById(R.id.ReceiverName)).append(bundle.get("ReceiverName").toString());
        ((TextView)v.findViewById(R.id.ReceiverContact)).append(bundle.get("ReceiverPhone").toString());
        ((TextView)v.findViewById(R.id.SenderName)).append(bundle.get("SenderName").toString());
        ((TextView)v.findViewById(R.id.SenderContact)).append(bundle.get("SenderPhone").toString());
        ((TextView)v.findViewById(R.id.Price)).setText("Rs."+bundle.get("Price").toString()+"/-");

        Button b1=v.findViewById(R.id.backbtn);
        b1.setOnClickListener(v1 -> {
                backpressed(v);
        });
        return v;
    }
    public void backpressed(View view){
        PartnerHistory fragment1 = new PartnerHistory();
        FragmentTransaction fragmentTransaction1 = getParentFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.content, fragment1);
        fragmentTransaction1.commit();
    }
}