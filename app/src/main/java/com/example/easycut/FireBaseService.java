package com.example.easycut;



import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FireBaseService {
    private static Set<Integer> mySet = new HashSet<>();

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
}
