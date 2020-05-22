package com.vaclmat.sdv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class IIStudents {

    @SerializedName("students_GETALL_R")
    @Expose
    private List<StudentsGETALLR> aerialist = new ArrayList<>();

    public List<StudentsGETALLR> getStudentsGETALLR() {
        return aerialist;
    }
}
