package com.example.deliverynestpartner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TodayDelivery extends Fragment {
    String username,date,user;
    SessionManager sessionManager;
    DatabaseReference reference;
    ListView l1;
    LottieAnimationView animationView;
    ArrayList<Delivery_POJO> arrayList=new ArrayList<>(50);
    String orderid,assignedto,bookoption,itemnametosend,loggedusername,notifypersonoption,orderdate,orderweight,parcelvalue,pickupaddress,
            preferbagoption,receiveraddress,receiverlandmark,receivername,receiverphone,senderlandmark,sendername,senderphone,status,price;
    int symbol;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext());
        HashMap<String, String> usersDetails = sessionManager.getUsersDetailsFromSession();
        username = usersDetails.get(SessionManager.KEY_USERNAME);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_today_delivery, container, false);
        animationView =v.findViewById(R.id.notFoundAnimation);
        l1=v.findViewById(R.id.listView);
        FirebaseApp.initializeApp(this.getContext());
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    orderid=dataSnapshot.getKey();
                        reference=FirebaseDatabase.getInstance().getReference("Orders").child(orderid);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Date todaysdate = new Date();
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    date = format.format(todaysdate);
                                    for(DataSnapshot datasnapshot:snapshot.getChildren())
                                    {
                                        if(datasnapshot.getKey().equals("OrderDate")){
                                            orderdate=datasnapshot.getValue().toString();
                                        }
                                        else if (datasnapshot.getKey().equals("Assigned_to"))
                                        {
                                            assignedto = datasnapshot.getValue().toString();
                                        }
                                        else if (datasnapshot.getKey().equals("Status"))
                                        {
                                            status = datasnapshot.getValue().toString();
                                            if (status == "Received" || status.equals("Received")) {
                                                symbol = R.drawable.solved;
                                            }
                                            else
                                            {
                                                symbol = R.drawable.incomplete;
                                            }
                                        }
                                        else if (datasnapshot.getKey().equals("BookOption")) {
                                            bookoption = datasnapshot.getValue().toString();
                                        }
                                        else if (datasnapshot.getKey().equals("ItemNameToSend")) {
                                            itemnametosend=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("NotifyPersonOption")){
                                            notifypersonoption=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("OrderWeight")){
                                            orderweight=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("ParcelValue")){
                                            parcelvalue=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("PickUpAddress")){
                                            pickupaddress=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("PreferBagOption")){
                                            preferbagoption=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("ReceiverAddress")){
                                            receiveraddress=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("ReceiverLandmark")){
                                            receiverlandmark=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("ReceiverName")){
                                            receivername=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("ReceiverPhone")){
                                            receiverphone=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("SenderLandmark")){
                                            senderlandmark=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("SenderName")){
                                            sendername=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("price")){
                                            price=datasnapshot.getValue().toString();
                                        }
                                        else if(datasnapshot.getKey().equals("SenderPhone")){
                                            senderphone=datasnapshot.getValue().toString();
                                        }
                                        else{

                                        }
                                    }
                                    if((orderdate.equals(date) || orderdate==date) && (assignedto=="In Process" || assignedto.equals("In Process"))){
                                        arrayList.add(new Delivery_POJO());
                                    }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }
}