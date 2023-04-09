package com.example.deliverynestpartner;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AssignOrder_Success extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_assignorder_success, container, false);
        int delay = 2000;
        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                TodayDelivery fragment1 = new TodayDelivery();
                FragmentTransaction fragmentTransaction1 = getParentFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.content, fragment1);
                TodayDelivery.adapter.notifyDataSetChanged();
                fragmentTransaction1.commit();
            }
        };
        handler.postDelayed(runnable, delay);
        return v;
    }
}
