package com.example.easycut;

public class Appointment implements IAppointment{
    private int _key;
    private int _type;
    private String _ClientID;
    private String _startTime;
    private String _endTime;


    public Appointment(int key,int type, String ClientID,String startTime, String endTime  ){
        _key = key;
        _type = type;
        _ClientID = ClientID;
        _startTime = startTime;
        _endTime = endTime;

    }

    @Override
    public int getKey() {
        return _key;
    }

    @Override
    public void setKey(int i) {
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
