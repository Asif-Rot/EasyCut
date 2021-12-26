package com.example.easycut;

import com.example.easycut.callInterface.*;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.easycut.objects.Appointment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FireBaseService {
    private static Set<String> stringSet = new HashSet<>();
    private static HashMap<String, Appointment> map_appoint_show=new HashMap<>();
//    private static final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    protected static Appointment appointment = new Appointment(null,null, null,null, null);
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    //this is how to get to child of a child
    public static void getHours(final UserListCallback myCallback, String date) {
        stringSet.clear();
        FirebaseDatabase.getInstance().getReference().child("Appointment").child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {
                            };
                            Map<String, Object> objectHashMap = snapshot.getValue(objectsGTypeInd);
                            assert objectHashMap != null;
                            ArrayList<Object> objectArrayList = new ArrayList<Object>(objectHashMap.values());
                            String hour = (String) objectArrayList.get(3);
                            stringSet.add(hour);
                        }
                        myCallback.onCallback(stringSet);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    //remove all slots that unavailable
    public static void getValidTimes(ArrayList<String> times) {
        for (String val : stringSet) {
            times.remove(val);
        }
    }

    /**
     * func service for get all turn for show to hairstyle
     *
     * @param myCallback -> help to pull data from data base
     * @param date       -> date for current day show turn
     */
    public static void getClientDriy(Context context, final appointShowCallBack myCallback, String date) {
        map_appoint_show.clear();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Appointment").child(date);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists())
                    return;
                GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                HashMap<String, Object> objectHashMap = snapshot.getValue(objectsGTypeInd);
                assert objectHashMap != null;
                for (DataSnapshot i : snapshot.getChildren()) {
                    String key = i.getKey();
                    String startTime = i.child("startTime").getValue().toString();
                    String clientID = i.child("clientID").getValue().toString();
                    String type = i.child("type").getValue().toString();
                    Appointment a = new Appointment(date, type, clientID, startTime, "0");
                    map_appoint_show.put(key, a);
                }
                myCallback.appointShowCallBack(map_appoint_show);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // for make toast if no turn in data base
    }


    /**
     * func to pull the current name of client from database
     *
     * @param myCallBack
     * @param id
     */
    public static void getFullName(final callBackFullName myCallBack, String id) {
        FirebaseDatabase.getInstance().getReference().child("Client").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String first = snapshot.child("firstName").getValue().toString();
                String last = snapshot.child("lastName").getValue().toString();
                String phone = snapshot.child("phoneNumber").getValue().toString();
                myCallBack.callBackFullName(first, last, phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * this func write appointment into the DB
     * @param spinTimes
     * @param spinHairCut
     * @param eText
     */
    public static void db_makeAppointment(Spinner spinTimes,Spinner spinHairCut, EditText eText) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        appointment.setClientID(user.getUid());
        appointment.setKey(eText.getText().toString());
        appointment.setStartTime(spinTimes.getSelectedItem().toString());
        appointment.setType(spinHairCut.getSelectedItem().toString());
        DatabaseReference reference = database.getReference("Appointment");
        reference.child(appointment.getKey()).child(getHour(appointment.getStartTime())).setValue(appointment);
        reference.child(appointment.getKey()).orderByKey();
    }

    public static String getHour(String hour) {
        int hourStart;
        switch (hour) {
            case "9:00":
                hourStart = 1;
                break;
            case "9:30":
                hourStart = 2;
                break;
            case "10:00":
                hourStart = 3;
                break;
            case "10:30":
                hourStart = 4;
                break;
            case "11:00":
                hourStart = 5;
                break;
            case "11:30":
                hourStart = 6;
                break;
            case "12:00":
                hourStart = 7;
                break;
            case "12:30":
                hourStart = 8;
                break;
            case "13:00":
                hourStart = 9;
                break;
            case "13:30":
                hourStart = 10;
                break;
            case "14:00":
                hourStart = 11;
                break;
            case "14:30":
                hourStart = 12;
                break;
            case "15:00":
                hourStart = 13;
                break;
            case "15:30":
                hourStart = 14;
                break;
            case "16:00":
                hourStart = 15;
                break;
            case "16:30":
                hourStart = 16;
                break;
            case "17:00":
                hourStart = 17;
                break;
            case "17:30":
                hourStart = 18;
                break;
            case "18:00":
                hourStart = 19;
                break;
            case "18:30":
                hourStart = 20;
                break;
            case "19:00":
                hourStart = 21;
                break;
            default:
                hourStart = -1;
                break;
        }
        return "" + hourStart;
    }

    public static void get_set_Product(final callBackProudct callProudct) {
        List<String> list = new LinkedList<>();
        FirebaseDatabase.getInstance().getReference().child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.getChildren()) {
                    String product = i.getValue().toString();
                    list.add(product);
                }
                callProudct.callBackProudct(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * func to get all user appointments
     * @param myCallback
     * @return arraylist with all user appointments
     */
    public static ArrayList<Appointment> getMyAppointments(final myAppointmentCallback myCallback){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        database.getReference().child("Appointment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot date : snapshot.getChildren()) {
                    for (DataSnapshot appTemp : date.getChildren()) {
                        if (appTemp.child("clientID").getValue().equals(user.getUid())) {
                            String type = appTemp.child("type").getValue().toString();
                            String start = appTemp.child("startTime").getValue().toString();
                            String end = "";

                            Appointment appointment = new Appointment(date.getKey(), type, user.getUid(), start, end);
                            appointmentList.add(appointment);
                        }
                    }
                }
                myCallback.onCallback(appointmentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return appointmentList;
    }

    public static void getuserID_DB(final callUserID myCallBack, String date, String time) {
        FirebaseDatabase.getInstance().getReference().child("Appointment").child(date).child(time).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String _id = snapshot.child("clientID").getValue().toString();
                myCallBack.getUserId(_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    /**
     * func to remove all appointment history. running every time someone sign in to the App.
     *
     * @throws ParseException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void cleanHistory() throws ParseException {
        System.out.println();
        SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy");
        Date today = new Date();
        database.getReference().child("Appointment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot x : snapshot.getChildren()) {
                    try {
                        Date temp = dtf.parse(x.getKey());
                        if (today.compareTo(temp) > 0 && !dtf.format(today).equals(dtf.format(temp))) {
                            x.getRef().removeValue();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * func to delete appointment from DB
     * @param appointment
     */
    public static void deleteAppointment(Appointment appointment){
        database.getReference().child("Appointment").child(appointment.getKey())
                .child(getHour(appointment.getStartTime()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }
}


