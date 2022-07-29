package com.vaclmat.MBv1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VidRec {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vid")
    @Expose
    private String vid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

}
