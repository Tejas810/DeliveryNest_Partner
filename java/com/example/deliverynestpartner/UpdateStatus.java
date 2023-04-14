package com.example.deliverynestpartner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateStatus extends Fragment {
    String DeliveryStatus;
    String Oid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_update_status, container, false);

        RadioGroup rd=v.findViewById(R.id.DeliveryStatus);
        rd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.statusOutForPickup:DeliveryStatus="Out For Pickup";
                                                break;
                    case R.id.statusInTransit:DeliveryStatus="In Transit";
                                                break;
                    case R.id.statusOutForDelivery:DeliveryStatus="Out For Delivery";
                                                break;
                    case R.id.statusDelivered:DeliveryStatus="Delivered";
                                                break;
                    case R.id.statusCompleted:DeliveryStatus="Completed";
                                                break;
                    case R.id.statusUnreachable:DeliveryStatus="Unreachable";
                                                break;
                    case R.id.statusInvalidAddress:DeliveryStatus="Invalid Address";
                                                break;
                    case R.id.statusDeniedToAccept:DeliveryStatus="Denied To Accept";
                                                break;
                }
                Oid=ShowDelivery.orderidtotrack;
                FirebaseApp.initializeApp(getContext());
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Orders").child(Oid);
                Map map=new HashMap();
                map.put("Status",DeliveryStatus);
                reference.updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getContext(), "Status set to "+DeliveryStatus, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Unable to Update Delivery Status", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return v;
    }
}