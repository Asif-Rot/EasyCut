package com.example.easycut;

public interface IUser {


    public String getLastName();
    public String getFirstName();
    public void setFirsName(String firstName);
    public void setLastName(String lastName);
    public void setPassword(String pass);
    public String getPassword();
    public String getPhoneNumber();
    public void setPhoneNumber(String phoneNumber);
    public String getEmail();
    public void setEmail(String email);
    public String getUserID();
    public void setUserID(String userID);
    public UserType getType();

}
