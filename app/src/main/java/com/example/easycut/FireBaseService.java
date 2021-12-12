package com.example.easycut;


import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FireBaseService {
    private static Set<String> stringSet = new HashSet<>();
    private static final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    protected static Appointment appointment = new Appointment(null,1, user.getUid(),null, null);
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    //this is how to get to child of a child
    public static void getHours(final UserListCallback myCallback, String date){
        stringSet.clear();
        FirebaseDatabase.getInstance().getReference().child("Appointment").child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            Map<String, Object> objectHashMap = snapshot.getValue(objectsGTypeInd);
                            assert objectHashMap != null;
                            ArrayList<Object> objectArrayList = new ArrayList<Object>(objectHashMap.values());
                            String hour = (String) objectArrayList.get(2);
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
    public static void getValidTimes(ArrayList<String> times){
        for (String val : stringSet){
            times.remove(val);
        }
    }

    public static void db_makeAppointment(Spinner spinTimes, EditText eText) {
        appointment.setKey(eText.getText().toString());
        appointment.setStartTime(spinTimes.getSelectedItem().toString());
        DatabaseReference reference = database.getReference("Appointment");
        reference.child(appointment.getKey()).child(getHour(appointment.getStartTime())).setValue(appointment);
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
