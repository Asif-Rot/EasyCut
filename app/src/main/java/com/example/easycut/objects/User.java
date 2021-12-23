package com.example.easycut.objects;
import com.example.easycut.Interface.*;
import com.example.easycut.Enum.UserType;

public class User implements IUser {
    protected String _firstName;
    protected String _lastName;
    protected String _email;
    protected String _pass;
    protected String _phoneNumber;
    protected String _userID;
    protected UserType _userType;

    public User(String _firstName, String _lastName,String _phoneNumber ,String _email , String _pass, String _userID) {
        this._firstName = _firstName;
        this._lastName = _lastName;
        this._email = _email;
        this._pass = _pass;
        this._phoneNumber = _phoneNumber;
        this._userID = _userID;
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
    public UserType getType() {
        return _userType;
    }

    @Override
    public String getPassword() {
        return this._pass;
    }

    @Override
    public void setPassword(String pass) {
        this._pass=pass;
    }


}
