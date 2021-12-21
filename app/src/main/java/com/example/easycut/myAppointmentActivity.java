package com.example.easycut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.easycut.callInterface.myAppointmentCallback;
import com.example.easycut.objects.Appointment;

import java.util.ArrayList;

public class myAppointmentActivity extends AppCompatActivity {
    TextView txtMyApp;
    Button back;
    ArrayList<Appointment> myAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);
        txtMyApp = (TextView) findViewById(R.id.showMyApp);
        back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myAppointmentActivity.this, ScreenUserActivity.class);
                startActivity(intent);
            }
        });


        myAppointment = FireBaseService.getMyAppointments(new myAppointmentCallback() {
            @Override
            public void onCallback(ArrayList<Appointment> arr) {
                String my_app = "";
                for(Appointment a : arr){
                    my_app += "Date: "+a.getDate()+"\t" +"Time: "+ a.getStartTime()+"\n";
                }
                txtMyApp.setTextSize(20);
                txtMyApp.setText(my_app);
            }
        });


    }
}