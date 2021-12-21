package com.example.easycut.callInterface;

import com.example.easycut.objects.Appointment;

import java.util.HashMap;

/**
 * interface for get all turn for pull from database
 */
public interface appointShowCallBack {
    void appointShowCallBack(HashMap<String, Appointment> x);

}
