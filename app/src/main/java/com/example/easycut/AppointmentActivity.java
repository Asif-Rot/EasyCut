package com.example.easycut;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;

public class AppointmentActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    String[] haircuts = {"", "Men's haircut" };
    private ArrayList<String> times = new ArrayList<>(Arrays.asList("", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
            "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00"));
    DatePickerDialog picker;
    EditText eText;
    Button showTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

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
                picker.show();
            }
        });

        //Spinner for haircuts
        Spinner spinHairCut = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapterHC = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, haircuts);
        adapterHC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHairCut.setAdapter(adapterHC);
        spinHairCut.setOnItemSelectedListener(this);

        //Spinner for times
        Spinner spinTimes = (Spinner) findViewById(R.id.timeSpinner);

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
                        public void onCallback(Set<Integer> value) {
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

        Button scheduleButton = (Button) findViewById(R.id.button);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!spinTimes.getSelectedItem().toString().equals("")) {
                    FireBaseService.db_makeAppointment(spinTimes, eText);
                    Toast.makeText(AppointmentActivity.this,
                            "Appointment has been scheduled for: " + FireBaseService.appointment.getKey()+ ", "
                                    + FireBaseService.appointment.getStartTime(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please pick time for your haircut", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        if (spin.getId() == R.id.spinner1){
            Toast.makeText(getApplicationContext(), "Selected User: "+haircuts[position] ,Toast.LENGTH_SHORT).show();
        }
        if (spin2.getId() == R.id.timeSpinner){
            Toast.makeText(getApplicationContext(), "Selected time: "+times.get(position) ,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //choose what to do when nothing selected.

    }

    public static String getHour(String hour) {
        int hourStart;
        switch (hour) {
            case "9:00":  hourStart = 1;
                break;
            case "9:30":  hourStart = 2;
                break;
            case "10:00":  hourStart = 3;
                break;
            case "10:30":  hourStart = 4;
                break;
            case "11:00":  hourStart = 5;
                break;
            case "11:30":  hourStart = 6;
                break;
            case "12:00":  hourStart = 7;
                break;
            case "12:30":  hourStart = 8;
                break;
            case "13:00":  hourStart = 9;
                break;
            case "13:30":  hourStart = 10;
                break;
            case "14:00":  hourStart = 11;
                break;
            case "14:30":  hourStart = 12;
                break;
            case "15:00":  hourStart = 13;
                break;
            case "15:30":  hourStart = 14;
                break;
            case "16:00":  hourStart = 15;
                break;
            case "16:30":  hourStart = 16;
                break;
            case "17:00":  hourStart = 17;
                break;
            case "17:30":  hourStart = 18;
                break;
            case "18:00":  hourStart = 19;
                break;
            case "18:30":  hourStart = 20;
                break;
            case "19:00":  hourStart = 21;
                break;
            default: hourStart = -1;
                break;
        }
        return ""+ hourStart;
    }
}