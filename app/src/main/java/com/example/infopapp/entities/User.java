package com.example.infopapp.entities;

import com.google.firebase.database.Exclude;

public class User {
    private String id, userType, firstName, lastName, displayName, email, userToken, phone,
            profileImageurl;

    public User() {
    } // default default constructor


    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //========================================= getters ===========================================


    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getUserType() {
        return userType;
    }

    public String getPhone() {
        return phone;
    }

    @Exclude
    public String getProfileImageurl() {
        return profileImageurl;
    }

    //=========================================Setters================================================//

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Exclude
    public void setProfileImageurl(String profileImageurl) {
        this.profileImageurl = profileImageurl;
    }
}
