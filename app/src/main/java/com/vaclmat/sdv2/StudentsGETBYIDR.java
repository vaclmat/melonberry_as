package com.vaclmat.sdv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentsGETBYIDR {
    @SerializedName("Student ID")
    @Expose
    private String studentID;
    @SerializedName("First Name")
    @Expose
    private String firstName;
    @SerializedName("last Name")
    @Expose
    private String lastName;
    @SerializedName("Gender Type")
    @Expose
    private String gender;

    public StudentsGETBYIDR(String studentID, String firstName, String lastName, String gender) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String genderType) {
        this.gender = gender;
    }

}
