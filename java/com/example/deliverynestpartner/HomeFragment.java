package com.example.deliverynestpartner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    String JobStatus="";
    String username;
    SessionManager sessionManager;
    Switch s1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this.getContext());
        HashMap<String, String> usersDetails = sessionManager.getUsersDetailsFromSession();
        username = usersDetails.get(SessionManager.KEY_USERNAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);

        s1=v.findViewById(R.id.jobStatus);
        FirebaseApp.initializeApp(this.getContext());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Partner").child(username);
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeStatus(buttonView,isChecked,reference);
            }
        });
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
}