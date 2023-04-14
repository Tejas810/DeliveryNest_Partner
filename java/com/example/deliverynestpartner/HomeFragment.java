package com.example.deliverynestpartner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    String JobStatus="";
    String username,fullname,status;
    DatabaseReference reference;
    SessionManager sessionManager;
    String orderdate,user,date;
    int TodayCount=0,DeliveryCount=0;
    SwitchMaterial s1;
    TextView t1,t2,t3,t4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this.getContext());
        HashMap<String, String> usersDetails = sessionManager.getUsersDetailsFromSession();
        username = usersDetails.get(SessionManager.KEY_USERNAME);
        fullname = usersDetails.get(SessionManager.KEY_FULLNAME);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        TextView t1=v.findViewById(R.id.WelcomeMsg);
        t1.append(fullname);
        t2=v.findViewById(R.id.TodayOrders);
        t3=v.findViewById(R.id.OrdersDelivered);
        t4=v.findViewById(R.id.MemberSince);

        s1=v.findViewById(R.id.jobStatus);
        FirebaseApp.initializeApp(this.getContext());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Partner").child(username);
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeStatus(buttonView,isChecked,reference);
            }
        });

        ShowTodaysOrdersCount();
        ShowOrdersDeliveredCount();
        ShowMemberSince();
        return v;
    }
    public void changeStatus(CompoundButton button,boolean status,DatabaseReference reference) {
        if (s1.isChecked()) {
            JobStatus = "Active";
            Map map = new HashMap();
            map.put("status", JobStatus);
            reference.updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(getContext(), "Status is now " + JobStatus, Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Unable to set status as " + JobStatus, Toast.LENGTH_SHORT).show();
                    s1.setChecked(false);
                }
            });
        } else {
            JobStatus = "Not Active";
            Map map = new HashMap();
            map.put("status", JobStatus);
            reference.updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(getContext(), "Status is now " + JobStatus, Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Unable to set status as " + JobStatus, Toast.LENGTH_SHORT).show();
                    s1.setChecked(true);
                }
            });
        }
    }
    public void ShowTodaysOrdersCount(){
        FirebaseApp.initializeApp(getContext());
        reference=FirebaseDatabase.getInstance().getReference().child("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    reference=FirebaseDatabase.getInstance().getReference().child("Orders").child(dataSnapshot.getKey());
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        if (dataSnapshot1.getKey().equals("OrderDate")) {
                            orderdate = dataSnapshot1.getValue().toString();
                            Date todaysdate = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            date = format.format(todaysdate);
                        }
                        else if (dataSnapshot1.getKey().equals("Status")) {
                            status = dataSnapshot1.getValue().toString();
                        }
                        else{

                        }
                    }
                    if ((!status.equals("Received") || status != "Received" ) && ( !status.equals("Cancelled") || status != "Cancelled" ) && ( orderdate.equals(date) || orderdate == date )) {
                        TodayCount++;
                    }
                }
                t2.setText("Today's Orders \n\n"+TodayCount);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void ShowOrdersDeliveredCount()
    {
        FirebaseApp.initializeApp(getContext());
        reference=FirebaseDatabase.getInstance().getReference().child("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    reference=FirebaseDatabase.getInstance().getReference().child("Orders").child(dataSnapshot.getKey());
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        if(dataSnapshot1.getKey().equals("Assigned_to")){
                            user=dataSnapshot1.getValue().toString();
                            if(user.equals(fullname) || user==fullname){
                                DeliveryCount++;
                            }
                        }
                    }
                }
                t3.append("Orders Delivered \n\n"+DeliveryCount);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void ShowMemberSince(){
        FirebaseApp.initializeApp(getContext());
        reference=FirebaseDatabase.getInstance().getReference().child("Partner").child(username);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    if(snapshot1.getKey().equals("JDate")){
                        t4.setText("Your are member since "+snapshot1.getValue().toString()+"\n\nThanks for being a valued member");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}