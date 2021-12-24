package com.example.easycut;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;

public class ScreenUserActivity extends AppCompatActivity {
    Button logOut;
    Button _makeAppointment;
    Button viewAppointment;
    private FirebaseAuth mAuth;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_user);
        mAuth = FirebaseAuth.getInstance();

        try {
            FireBaseService.cleanHistory();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        logOut = (Button) findViewById(R.id.log_out);
        _makeAppointment=(Button)findViewById(R.id.make_appoint) ;
        _makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenUserActivity.this, AppointmentActivity.class);
                startActivity(intent);
            }
        });

        viewAppointment = findViewById(R.id.btnViewApp);
        viewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenUserActivity.this, myAppointmentActivity.class);
                startActivity(intent);
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ScreenUserActivity.this, MainActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//makesure user cant go back
                startActivity(intent);
            }

        });

    }
}