package com.example.easycut;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    String[] haircuts = {"", "Men's haircut" };
    String[] times = {"", "10:00","10:30","11:00" };
    DatePickerDialog picker;
    EditText eText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        //date picker
        eText=(EditText) findViewById(R.id.datePicker);
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
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        //Spinner for haircuts
        Spinner spinHairCut = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapterHC = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,haircuts);
        adapterHC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHairCut.setAdapter(adapterHC);
        spinHairCut.setOnItemSelectedListener(this);

        //Spinner for times
        Spinner spinTimes = (Spinner) findViewById(R.id.timeSpinner);
        ArrayAdapter<String> adapterT = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,times);
        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTimes.setAdapter(adapterT);
        spinTimes.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        if (spin.getId() == R.id.spinner1){
            Toast.makeText(getApplicationContext(), "Selected User: "+haircuts[position] ,Toast.LENGTH_SHORT).show();
        }
        if (spin2.getId() == R.id.timeSpinner){
            Toast.makeText(getApplicationContext(), "Selected time: "+times[position] ,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //choose what to do when nothing selected.

    }
}