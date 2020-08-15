package com.superboringservicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {

    TimerBService boundService ;
    boolean isServiceBound = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnStartService = view.findViewById(R.id.btnStartService);
        Button btnStopService = view.findViewById(R.id.btnStopService);
        Button btnShow = view.findViewById(R.id.btnShow);
        TextView tvTime = view.findViewById(R.id.tvTime);
        btnShow.setOnClickListener(view1 -> {
            tvTime.setText(boundService.getTimestamp());
        });
        btnStartService.setOnClickListener(view2 -> {
            Intent i = new Intent(getActivity(), TimerBService.class);
            // binding service to activity
            getActivity().bindService(i, serviceConn, Context.BIND_AUTO_CREATE);
            Toast.makeText(getActivity(), "service bound", Toast.LENGTH_SHORT).show();
        });
        btnStopService.setOnClickListener(view3 -> {
            if(isServiceBound){
                getActivity().unbindService(serviceConn);
                isServiceBound = false;
            }
            Toast.makeText(getActivity(), "service disconnected to activity", Toast.LENGTH_SHORT).show();
        });

    }

    ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            isServiceBound = true;
            TimerBService.TimeBinder binder = (TimerBService.TimeBinder) iBinder;
            boundService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceBound = false;
        }
    };
}