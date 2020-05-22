package com.vaclmat.sdv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentsGETALLR {

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
    private String genderType;

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

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

}
