package com.example.easycut;

import java.util.HashMap;

public class Appointment implements IAppointment{
    private String _key;
    private int _type;
    private String _ClientID;
    private String _startTime;
    private String _endTime;



    public Appointment(String key,int type, String ClientID,String startTime, String endTime  ){
        _key = key;
        _type = type;
        _ClientID = ClientID;
        _startTime = startTime;
        _endTime = endTime;

    }


    @Override
    public String getKey() {
        return _key;
    }

    @Override
    public void setKey(String i) {
        _key = i;
    }

    @Override
    public int getType() {
        return _type;
    }

    @Override
    public void setType(int i) {
        _type = i;
    }

    @Override
    public String getClientID() {
        return _ClientID;
    }

    @Override
    public void getClientName(String s) {
        _ClientID = s;
    }

    @Override
    public String getDate() {
        return null;
    }

    @Override
    public void setDate(String s) {

    }

    @Override
    public String getStartTime() {
        return _startTime;
    }

    @Override
    public void setStartTime(String s) {
        _startTime = s;
    }

    @Override
    public String getEndTime() {
        return _endTime;
    }

    @Override
    public void setEndTime(String s) {
        _endTime = s;
    }

    @Override
    public int getDuration() {
        return 0;
    }
}
