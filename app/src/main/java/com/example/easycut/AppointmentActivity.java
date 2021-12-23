package com.example.easycut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easycut.callInterface.UserListCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;

public class AppointmentActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    String[] haircuts = {"", "Men's haircut" };
    private ArrayList<String> times = new ArrayList<>(Arrays.asList("", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
            "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00"));
    DatePickerDialog picker;
    EditText eText;
    Button showTimes;
    Button scheduleButton;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Notification manager
        notificationManager = NotificationManagerCompat.from(this);

        //date picker
        eText = (EditText) findViewById(R.id.datePicker);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();
            }
        });

        //Spinner for haircuts
        Spinner spinHairCut = (Spinner) findViewById(R.id.spinnerHairType);
        ArrayAdapter<String> adapterHC = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, haircuts);
        adapterHC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHairCut.setAdapter(adapterHC);
        spinHairCut.setOnItemSelectedListener(this);

        //Spinner for times
        Spinner spinTimes = (Spinner) findViewById(R.id.spinnerTime);
        //button for show time
        showTimes = (Button) findViewById(R.id.btnChooseTime);
        showTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eText.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "Please pick a date first", Toast.LENGTH_SHORT).show();
                else if (spinHairCut.getSelectedItem().toString().equals(""))
                        Toast.makeText(getApplicationContext(),"Please pick an haircut first", Toast.LENGTH_SHORT).show();
                    else{
                    spinTimes.setEnabled(true);      //to enable
                    spinTimes.setClickable(true);
                    FireBaseService.getHours(new UserListCallback() {
                        @Override
                        public void onCallback(Set<String> value) {
                            FireBaseService.getValidTimes(times);
                            ArrayAdapter<String> adapterT = new ArrayAdapter<>(AppointmentActivity.this, android.R.layout.simple_spinner_item, times);
                            adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinTimes.setAdapter(adapterT);
                            spinTimes.setOnItemSelectedListener(AppointmentActivity.this);
                            times = new ArrayList<>(Arrays.asList("", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
                                    "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00"));
                        }
                    },eText.getText().toString());
                }
            }
        });

        scheduleButton = (Button) findViewById(R.id.button);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!spinTimes.getSelectedItem().toString().equals("")) {
                    FireBaseService.db_makeAppointment(spinTimes, eText);
                    sendOnChannel(v);
                    Intent intent = new Intent(AppointmentActivity.this, BookingApproved.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please pick time for your haircut", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendOnChannel (View v) {
        String message = "Your appointment has been scheduled";
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, NotificationService.CHANNEL)
                .setSmallIcon(R.drawable.ic_cut)
                .setContentTitle(message)
                .setContentText(FireBaseService.appointment.getKey()+ ", "
                        + FireBaseService.appointment.getStartTime())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true);

        Intent intent_notification = new Intent(AppointmentActivity.this, myAppointmentActivity.class);
        intent_notification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent_notification.putExtra("message",message);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent=PendingIntent
                .getActivity(AppointmentActivity.this,0,intent_notification,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        notificationManager.notify(1, notification.build());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}