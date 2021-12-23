package com.example.easycut;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycut.callInterface.appointShowCallBack;
import com.example.easycut.callInterface.callBackFullName;
import com.example.easycut.objects.Appointment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

/**
 * activity of diary for hairstyle
 */
public class DiaryHairStylistActivity extends AppCompatActivity {
    DatePickerDialog picker;
    HashMap<String, TextView> map_txt;
    final int VISIBLE = 0x00000000;
    final int INVISIBLE = 0x00000004;
    TextView text1,text2,text10,text10_30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_hs);

        //back button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        init_map();
        EditText datapikerHS=(EditText) findViewById(R.id.datePickerHS);
        datapikerHS.setInputType(InputType.TYPE_NULL);
        // chose the date for show all turn in current day
        datapikerHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(DiaryHairStylistActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        datapikerHS.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        //fun to pull data for show turn client
                        showDriy(datapikerHS.getText().toString());
                    }
                }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();

            }
        });

    }

    /**
     * func to get all turn in current day for show to hair style
     * @param date = The exact day you want to see the diary
     */
    public void showDriy(String date){
        for(String i:map_txt.keySet()){
            map_txt.get(i).setText(null);
            map_txt.get(i).setVisibility(View.GONE);

        }
        FireBaseService.getClientDriy(new appointShowCallBack() {
            /**
             * fun to pull all client in current day
             * @param x -> hash map for time turn and information about client and turn
             */
            @Override
            public void appointShowCallBack(HashMap<String, Appointment> x) {
                if(!x.isEmpty()){
                    for (String time : x.keySet()) {
                        Appointment p = x.get(time);
                        FireBaseService.getFullName(new callBackFullName() {
                            /**
                             *
                             * @param first = the first name of client
                             * @param last = last name of client
                             */
                            @Override
                            public void callBackFullName(String first, String last) {
                                map_txt.get(time).setText("time : " + p.getStartTime() + " , " + "first name: " + first + " , " + "last name: " + last);
                                map_txt.get(time).setVisibility(VISIBLE);
                            }
                        }, p.getClientID());

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Empty diary", Toast.LENGTH_SHORT).show();
                }
            }
        }, date);
    }
    /**
     * func to init the map for pull from database
     * */
    public void init_map(){
        map_txt=new HashMap<String, TextView>();
        map_txt.put("1",((TextView) findViewById(R.id.textTime_1)));
        map_txt.put("2",((TextView) findViewById(R.id.textTime_2)));
        map_txt.put("3",((TextView) findViewById(R.id.textTime_3)));
        map_txt.put("4",((TextView) findViewById(R.id.textTime_4)));
        map_txt.put("5",((TextView) findViewById(R.id.textTime_5)));
        map_txt.put("6",((TextView) findViewById(R.id.textTime_6)));
        map_txt.put("7",((TextView) findViewById(R.id.textTime_7)));
        map_txt.put("8",((TextView) findViewById(R.id.textTime_8)));
        map_txt.put("9",((TextView) findViewById(R.id.textTime_9)));
        map_txt.put("10",((TextView) findViewById(R.id.textTime_10)));
        map_txt.put("11",((TextView) findViewById(R.id.textTime_11)));
        map_txt.put("12",((TextView) findViewById(R.id.textTime_12)));
        map_txt.put("13",((TextView) findViewById(R.id.textTime_13)));
        map_txt.put("14",((TextView) findViewById(R.id.textTime_14)));
        map_txt.put("15",((TextView) findViewById(R.id.textTime_15)));
        map_txt.put("16",((TextView) findViewById(R.id.textTime_16)));
        map_txt.put("17",((TextView) findViewById(R.id.textTime_17)));
        map_txt.put("18",((TextView) findViewById(R.id.textTime_18)));
        map_txt.put("19",((TextView) findViewById(R.id.textTime_19)));
        map_txt.put("20",((TextView) findViewById(R.id.textTime_20)));
        map_txt.put("21",((TextView) findViewById(R.id.textTime_21)));
    }
    public void showClientTurn(String date){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(date);

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