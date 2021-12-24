package com.example.easycut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.easycut.callInterface.myAppointmentCallback;
import com.example.easycut.objects.Appointment;

import java.util.ArrayList;

public class myAppointmentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private AppointmentAdapter adapter;
    ArrayList<Appointment> myAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);

        //back button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        myAppointment = FireBaseService.getMyAppointments(new myAppointmentCallback() {
            @Override
            public void onCallback(ArrayList<Appointment> arr) {
                recyclerView = findViewById(R.id.normal_rec);
                recyclerView.setLayoutManager(new LinearLayoutManager(myAppointmentActivity.this));
                myAppointment = new ArrayList<>();
                adapter = new AppointmentAdapter(myAppointmentActivity.this, arr);
                recyclerView.setAdapter(adapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(myAppointmentActivity.this, LinearLayoutManager.VERTICAL));

            }
        });

    }

    //back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}