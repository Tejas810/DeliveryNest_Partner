package com.example.deliverynestpartner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    public static DeliverysAdapter adapter;
    SessionManager sessionManager;
    DatabaseReference reference;
    ListView l1;
    LottieAnimationView animationView;
    ArrayList<Delivery_POJO> arrayList=new ArrayList<>(50);
    String orderid,assignedto,bookoption,itemnametosend,loggedusername,notifypersonoption,orderdate,orderweight,parcelvalue,pickupaddress,
            preferbagoption,receiveraddress,receiverlandmark,receivername,receiverphone,senderlandmark,sendername,senderphone,status,price;
    String oid;
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
        animationView.setVisibility(View.VISIBLE);
        l1=v.findViewById(R.id.listView);
        FirebaseApp.initializeApp(this.getContext());
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot.getChildrenCount());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    orderid = dataSnapshot.getKey();
                    ShowDeliveries(orderid);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }
    public void ShowDeliveries(String oid){
        orderid=oid;
        reference=FirebaseDatabase.getInstance().getReference("Orders").child(orderid);
        reference.addValueEventListener(new ValueEventListener()
        {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
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
                                     orderid=oid;
                                 }
                                if((orderdate.equals(date) || orderdate==date) && (assignedto=="In Process" || assignedto.equals("In Process")) && (status!="Cancelled" || !status.equals("Cancelled")) && (status!="Received" || !status.equals("Received"))){
                                    animationView.setVisibility(View.GONE);
                                    l1.setVisibility(View.VISIBLE);
                                    arrayList.add(new Delivery_POJO(orderid,assignedto,bookoption,itemnametosend,loggedusername,notifypersonoption,orderdate,orderweight,parcelvalue,pickupaddress,preferbagoption,receiveraddress,receiverlandmark,receivername,receiverphone,senderlandmark,sendername,senderphone,status,price));
                                    adapter=new DeliverysAdapter(getContext(),R.layout.delivery_layout,arrayList);
                                    l1.setAdapter(adapter);
                                }
                            l1.setOnItemClickListener(new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                {
                                    Bundle bundle=new Bundle();
                                    bundle.putString("OrderId",arrayList.get(position).getOrderid());
                                    bundle.putString("Status",arrayList.get(position).getStatus());
                                    bundle.putString("AssignedTo",arrayList.get(position).getAssignedto());
                                    bundle.putString("BookOption",arrayList.get(position).getBookoption());
                                    bundle.putString("ItemNameToSend",arrayList.get(position).getItemnametosend());
                                    bundle.putString("NotifyPersonOption",arrayList.get(position).getNotifypersonoption());
                                    bundle.putString("OrderDate",arrayList.get(position).getOrderdate());
                                    bundle.putString("OrderWeight",arrayList.get(position).getOrderweight());
                                    bundle.putString("ParcelValue",arrayList.get(position).getParcelvalue());
                                    bundle.putString("PickUpAddress",arrayList.get(position).getPickupaddress());
                                    bundle.putString("PreferBagOption",arrayList.get(position).getPreferbagoption());
                                    bundle.putString("ReceiverAddress",arrayList.get(position).getReceiveraddress());
                                    bundle.putString("ReceiverLandmark",arrayList.get(position).getReceiverlandmark());
                                    bundle.putString("ReceiverName",arrayList.get(position).getReceivername());
                                    bundle.putString("ReceiverPhone",arrayList.get(position).getReceiverphone());
                                    bundle.putString("SenderLandmark",arrayList.get(position).getSenderlandmark());
                                    bundle.putString("SenderName",arrayList.get(position).getSendername());
                                    bundle.putString("SenderPhone",arrayList.get(position).getSenderphone());
                                    bundle.putString("Price",arrayList.get(position).getPrice());
                                    ShowDelivery fragment=new ShowDelivery();
                                    fragment.setArguments(bundle);
                                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.content, fragment, "");
                                    fragmentTransaction.commit();
                                }
                            });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
        });
    }
}