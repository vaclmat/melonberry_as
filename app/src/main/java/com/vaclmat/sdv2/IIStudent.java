package com.vaclmat.sdv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class IIStudent {

    @SerializedName("students_GETBYID_R")
    @Expose
    private StudentsGETBYIDR studentsGETBYIDR;

        public StudentsGETBYIDR getStudentsGETBYIDR() {
            return studentsGETBYIDR;
        }



}
