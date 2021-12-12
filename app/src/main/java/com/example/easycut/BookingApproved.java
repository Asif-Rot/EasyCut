package com.example.easycut;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class BookingApproved extends AppCompatActivity  {
    TextView details;
    Button homeBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_approved);
        details = findViewById(R.id.appointmentDetails);
        details.setText("Appointment has been scheduled for: " + FireBaseService.appointment.getKey()+ ", "
                + FireBaseService.appointment.getStartTime());
        details.setTextSize(18);

        homeBtn = findViewById(R.id.backHome);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingApproved.this, ScreenUser.class);
                startActivity(intent);
            }
        });

    }
}