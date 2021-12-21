package com.example.easycut.callInterface;

import com.example.easycut.objects.Appointment;

import java.util.ArrayList;

public interface myAppointmentCallback {
    void onCallback(ArrayList<Appointment> arr);
}
