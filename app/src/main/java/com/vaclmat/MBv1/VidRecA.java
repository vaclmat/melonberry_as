package com.vaclmat.MBv1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VidRecA {

    @SerializedName("vid_record_array")
    @Expose
    private List<VidRec> aerialist = new ArrayList<>();

    public List<VidRec> getVidRec() {
        return aerialist;
    }
}
