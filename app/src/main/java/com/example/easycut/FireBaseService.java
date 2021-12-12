package com.example.easycut;


import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FireBaseService {
    private static Set<Integer> mySet = new HashSet<>();
    private static final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    protected static Appointment appointment = new Appointment(null,1, user.getUid(),null, null);
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    //this is how to get to child of a child
    public static void getHours(final UserListCallback myCallback, String date){
        mySet.clear();
        FirebaseDatabase.getInstance().getReference().child("Appointment").child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String appTime = snapshot.getKey();
                            assert appTime != null;
                            mySet.add(Integer.parseInt(appTime));
                        }
                        myCallback.onCallback(mySet);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public static void getValidTimes(ArrayList<String> times){
        for (int i : mySet){
            times.remove(i);
        }
    }

    public static void db_makeAppointment(Spinner spinTimes, EditText eText) {
        appointment.setKey(eText.getText().toString());
        appointment.setStartTime(spinTimes.getSelectedItem().toString());
        DatabaseReference reference = database.getReference("Appointment");
        reference.child(appointment.getKey()).child(AppointmentActivity.getHour(appointment.getStartTime())).setValue(appointment);
    }
}
