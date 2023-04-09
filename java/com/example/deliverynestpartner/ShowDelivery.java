package com.example.deliverynestpartner;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class ShowDelivery extends Fragment {
    String username,fullname;
    SessionManager sessionManager;
    private LoadingDialog aLodingDialog;
    FrameLayout fr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_show_delivery, container, false);

        sessionManager = new SessionManager(getContext());
        HashMap<String, String> usersDetails = sessionManager.getUsersDetailsFromSession();
        username = usersDetails.get(SessionManager.KEY_USERNAME);
        fullname = usersDetails.get(SessionManager.KEY_FULLNAME);

        Button b1=v.findViewById(R.id.backbtn);
        b1.setOnClickListener(r->{
            backpressed(v);
        });
        aLodingDialog = new LoadingDialog(getContext());
        Bundle bundle=getArguments();
        fr=v.findViewById(R.id.Framelayout);
        ((TextView)v.findViewById(R.id.OrderId)).append(bundle.get("OrderId").toString());
        ((TextView)v.findViewById(R.id.Status)).append(bundle.get("Status").toString());
        ((TextView)v.findViewById(R.id.AssignedTo)).append(bundle.get("AssignedTo").toString());
        ((TextView)v.findViewById(R.id.BookOption)).append(bundle.get("BookOption").toString());
        ((TextView)v.findViewById(R.id.ItemNameToSend)).append(bundle.get("ItemNameToSend").toString());
        ((TextView)v.findViewById(R.id.OrderWeight)).append(bundle.get("OrderWeight").toString());
        ((TextView)v.findViewById(R.id.SenderAddress)).append(bundle.get("PickUpAddress").toString());
        ((TextView)v.findViewById(R.id.BagPrefered)).append(bundle.get("PreferBagOption").toString());
        ((TextView)v.findViewById(R.id.ReceiverAddress)).append(bundle.get("ReceiverAddress").toString());
        ((TextView)v.findViewById(R.id.ReceiverName)).append(bundle.get("ReceiverName").toString());
        ((TextView)v.findViewById(R.id.ReceiverContact)).append(bundle.get("ReceiverPhone").toString());
        ((TextView)v.findViewById(R.id.SenderName)).append(bundle.get("SenderName").toString());
        ((TextView)v.findViewById(R.id.SenderContact)).append(bundle.get("SenderPhone").toString());
        ((TextView)v.findViewById(R.id.Price)).setText("Rs."+bundle.get("Price").toString()+"/-");

        Button assignOrderBtn=v.findViewById(R.id.AssignOrderBtn);
        assignOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignOrder(bundle.get("OrderId").toString());
            }
        });
        return v;
    }
    public void AssignOrder(String Oid){
        FirebaseApp.initializeApp(getContext());
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Orders").child(Oid);
        Map map=new HashMap();
        map.put("Assigned_to",fullname);
        map.put("Status","Assigned");
        reference.updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                loader();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error : Unable to assign this order to you", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loader() {
        aLodingDialog.show();

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                aLodingDialog.cancel();
                AssignOrder_Success fragment1 = new AssignOrder_Success();
                FragmentTransaction fragmentTransaction1 = getParentFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.content, fragment1);
                fragmentTransaction1.commit();
            }
        };
        handler.postDelayed(runnable, 3000);
    }
    public void backpressed(View view){
        TodayDelivery fragment1 = new TodayDelivery();
        FragmentTransaction fragmentTransaction1 = getParentFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.content, fragment1);
        fragmentTransaction1.commit();
    }
}