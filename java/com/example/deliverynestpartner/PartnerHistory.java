package com.example.deliverynestpartner;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PartnerHistory extends Fragment {
    String username,fullname,user;
    DatabaseReference reference;
    OrdersAdapter customAdapter;
    ListView l1;
    ArrayList<Order_POJO> arrayList = new ArrayList();
    LottieAnimationView animationView;
    SessionManager sessionManager;
    String orderid,assignedto,bookoption,itemnametosend,loggedusername,notifypersonoption,orderdate,orderweight,parcelvalue,pickupaddress,
            preferbagoption,receiveraddress,receiverlandmark,receivername,receiverphone,senderlandmark,sendername,senderphone,status,price;
    int symbol;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_partner_history, container, false);
        sessionManager = new SessionManager(this.getContext());
        HashMap<String, String> usersDetails = sessionManager.getUsersDetailsFromSession();
        username = usersDetails.get(SessionManager.KEY_USERNAME);
        fullname = usersDetails.get(SessionManager.KEY_FULLNAME);

        l1=v.findViewById(R.id.listView);
        l1.setVisibility(View.GONE);
        animationView=v.findViewById(R.id.notFoundAnimation);
        animationView.setVisibility(View.VISIBLE);
        reference= FirebaseDatabase.getInstance().getReference().child("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ShowHistory(dataSnapshot.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }
    public void ShowHistory(String oid) {

        reference=FirebaseDatabase.getInstance().getReference().child("Orders").child(oid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                    for(DataSnapshot datasnapshot:snapshot.getChildren())
                    {
                        if (datasnapshot.getKey().equals("Assigned_to"))
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
                        else if (datasnapshot.getKey().equals("LoggedUsername")) {
                            user=datasnapshot.getValue().toString();
                        }
                        else if (datasnapshot.getKey().equals("ItemNameToSend")) {
                            itemnametosend=datasnapshot.getValue().toString();
                        }
                        else if(datasnapshot.getKey().equals("NotifyPersonOption")){
                            notifypersonoption=datasnapshot.getValue().toString();
                        }
                        else if(datasnapshot.getKey().equals("OrderDate")){
                            orderdate=datasnapshot.getValue().toString();
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
                    if((assignedto.equals(fullname) || assignedto==fullname)){
                        l1.setVisibility(View.VISIBLE);
                        animationView.setVisibility(View.GONE);
                        arrayList.add(new Order_POJO(symbol,orderid,assignedto,bookoption,itemnametosend,notifypersonoption,orderdate,orderweight,parcelvalue,pickupaddress,preferbagoption,receiveraddress,receiverlandmark,receivername,receiverphone,senderlandmark,sendername,senderphone,status,price));
                    }
                    customAdapter=new OrdersAdapter(getContext(),R.layout.orderhistory_layout,arrayList);
                    l1.setAdapter(customAdapter);
                    l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

                            ViewOrder fragment1 = new ViewOrder();
                            fragment1.setArguments(bundle);
                            FragmentTransaction fragmentTransaction1 = getParentFragmentManager().beginTransaction();
                            fragmentTransaction1.replace(R.id.content, fragment1);
                            fragmentTransaction1.commit();
                        }
                    });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}