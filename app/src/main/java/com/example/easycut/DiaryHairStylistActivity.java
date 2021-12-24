package com.example.easycut;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycut.callInterface.appointShowCallBack;
import com.example.easycut.callInterface.callBackFullName;
import com.example.easycut.callInterface.callUserID;
import com.example.easycut.objects.Appointment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    EditText datapikerHS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_hs);



        init_map();
        datapikerHS = (EditText) findViewById(R.id.datePickerHS);
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

//        Button back = findViewById(R.id.returnHS);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DiaryHairStylistActivity.this, ScreenHairStylistActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    /**
     * func to get all turn in current day for show to hair style
     *
     * @param date = The exact day you want to see the diary
     */
    public void showDriy(String date) {
        for (String i : map_txt.keySet()) {
            TextView myTextView = map_txt.get(i);
            myTextView.setText(null);
            myTextView.setVisibility(View.GONE);
        }
        showD(date);
        System.out.println();

    }

    public void showD(String date){
        Context context = getApplicationContext();
        FireBaseService.getClientDriy(context, new appointShowCallBack() {
            /**
             * fun to pull all client in current day
             * @param x -> hash map for time turn and information about client and turn
             */
            @Override
            public void appointShowCallBack(HashMap<String, Appointment> x) {
                if ((!x.keySet().isEmpty() && (!x.values().isEmpty()))) {
                    Toast.makeText(getApplicationContext(), "Bring Appointment", Toast.LENGTH_SHORT).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 0.5s
                            for (String time : x.keySet()) {
                                Appointment p = x.get(time);
                                FireBaseService.getFullName(new callBackFullName() {
                                    /**
                                     *
                                     * @param first = the first name of client
                                     * @param last = last name of client
                                     */
                                    @Override
                                    public void callBackFullName(String first, String last, String phone) {
                                        map_txt.get(time).setText(" * Time :   ֿ" + p.getStartTime() + "            Type hair cut :   "+p.getType()+ "  \n" + "    First name :   " +
                                                first + " \n " + "   Last name :   " + last + "         Phone :   " + phone + "\n_____________________________________________________________________");
                                        map_txt.get(time).setVisibility(VISIBLE);
                                    }
                                }, p.getClientID());
                            }
                        }
                    }, 500);

                } else {
                    Toast.makeText(getApplicationContext(), "Empty Diary", Toast.LENGTH_SHORT).show();
                }
            }
        }, date);
    }
    /**
     * func to init the map for pull from database
     */
    public void init_map() {
        map_txt = new HashMap<String, TextView>();
        map_txt.put("1", ((TextView) findViewById(R.id.textTime_1)));
        map_txt.put("2", ((TextView) findViewById(R.id.textTime_2)));
        map_txt.put("3", ((TextView) findViewById(R.id.textTime_3)));
        map_txt.put("4", ((TextView) findViewById(R.id.textTime_4)));
        map_txt.put("5", ((TextView) findViewById(R.id.textTime_5)));
        map_txt.put("6", ((TextView) findViewById(R.id.textTime_6)));
        map_txt.put("7", ((TextView) findViewById(R.id.textTime_7)));
        map_txt.put("8", ((TextView) findViewById(R.id.textTime_8)));
        map_txt.put("9", ((TextView) findViewById(R.id.textTime_9)));
        map_txt.put("10", ((TextView) findViewById(R.id.textTime_10)));
        map_txt.put("11", ((TextView) findViewById(R.id.textTime_11)));
        map_txt.put("12", ((TextView) findViewById(R.id.textTime_12)));
        map_txt.put("13", ((TextView) findViewById(R.id.textTime_13)));
        map_txt.put("14", ((TextView) findViewById(R.id.textTime_14)));
        map_txt.put("15", ((TextView) findViewById(R.id.textTime_15)));
        map_txt.put("16", ((TextView) findViewById(R.id.textTime_16)));
        map_txt.put("17", ((TextView) findViewById(R.id.textTime_17)));
        map_txt.put("18", ((TextView) findViewById(R.id.textTime_18)));
        map_txt.put("19", ((TextView) findViewById(R.id.textTime_19)));
        map_txt.put("20", ((TextView) findViewById(R.id.textTime_20)));
        map_txt.put("21", ((TextView) findViewById(R.id.textTime_21)));

        for (String x : map_txt.keySet()) {
            map_txt.get(x).setOnClickListener(onClickListener);
        }

    }

    public void showClientTurn(String date) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(date);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v1) {
            AlertDialog.Builder mBulider = new AlertDialog.Builder(DiaryHairStylistActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_cancel_turn, null);
            TextView t = (TextView) mView.findViewById(R.id.if_cancel);
            int idd = v1.getId();
            TextView x = (TextView) findViewById(idd);
            Button yes = (Button) mView.findViewById(R.id.yes);
            Button no = (Button) mView.findViewById(R.id.no);
            String s = x.getText().toString();
            s = s.replace("*",
                    "");
            s = s.replace("Type hair cut",
                    "\nType hair cut");
            s = s.replace("Phone",
                    "\nPhone");
            s = s.replaceAll("_",
                    "");
            t.setText("\n If you want cancel this turn : \n\n" + s);
            mBulider.setView(mView);
            AlertDialog dialog = mBulider.create();
            dialog.show();
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    v2 = v1;
                    String correct;
                    for (String x : map_txt.keySet()) {
                        boolean b = map_txt.get(x).equals(v2);
                        if (b == true) {
                            FireBaseService.getuserID_DB(new callUserID() {
                                @Override
                                public void getUserId(String userID) {
                                    //FirebaseDatabase.getInstance().getReference().child("Client").child(userID).child("msg").setValue(1);
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query applesQuery = ref.child("Appointment").child(datapikerHS.getText().toString()).child(x);
                                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            dataSnapshot.getRef().removeValue();
                                            Toast.makeText(getApplicationContext(), "Remove successful", Toast.LENGTH_LONG).show();
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // Do something after 5s = 5000ms
                                                    dialog.dismiss();
                                                }
                                            }, 1000);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.e(TAG, "onCancelled", databaseError.toException());
                                        }
                                    });
                                }
                            }, datapikerHS.getText().toString(), x);

                        }
                    }
                }
            });
        }
    };
}