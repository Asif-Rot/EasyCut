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
        return 0;
    }

    @Override
    public void setKey(String s) {

    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void setType(int i) {

    }

    @Override
    public String getClientID() {
        return null;
    }

    @Override
    public void getClientName(String s) {

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
        return null;
    }

    @Override
    public void setStartTime(int i) {

    }

    @Override
    public String getEndTime() {
        return null;
    }

    @Override
    public void setEndTime(int i) {

    }

    @Override
    public int getDuration() {
        return 0;
    }
}
