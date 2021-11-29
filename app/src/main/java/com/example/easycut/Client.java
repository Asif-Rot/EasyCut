package com.example.easycut;

public class Client implements clientInterface, IUser {
    final String _userTypeClient = "client";
    String _firstName;
    String _lastName;
    String _email;
    String _pass;
    String _phoneNumber;
    String _userID;

    public Client(String _firstName, String _lastName, String _email, String _pass, String _phoneNumber, String _userID) {
        this._firstName=_firstName;
        this._lastName=_lastName;
        this._email=_email;
        this._pass=_pass;
        this._phoneNumber=_phoneNumber;
        this._userID=_userID;
    }

    @Override
    public void makeAppoitment() {
    }

    @Override
    public void cancelAppoitment() {
    }

    @Override
    public String getType() {
        return this._userTypeClient;
    }

    @Override
    public String getFirstName() {
        return this._firstName;
    }

    @Override
    public String getLastName() {
        return this._lastName;
    }

    @Override
    public void setFirsName(String firstName) {
        this._firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this._lastName = lastName;
    }

    @Override
    public String getPhoneNumber() {
        return this._phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this._phoneNumber = phoneNumber;
    }

    @Override
    public String getEmail() {
        return this._email;
    }

    @Override
    public void setEmail(String email) {
        this._email = email;
    }

    @Override
    public String getUserID() {
        return this._userID;
    }

    @Override
    public void setUserID(String userID) {
        this._userID = userID;
    }

    @Override
    public String getPassword() {
        return this._pass;
    }

    @Override
    public void setPassword(String pass) {
        this._pass = pass;
    }
}
