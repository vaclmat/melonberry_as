package com.vaclmat.MBv1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoRec {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("videoname")
    @Expose
    private String videoname;
    @SerializedName("linktv")
    @Expose
    private String linktv;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public String getLinktv() {
        return linktv;
    }

    public void setLinktv(String linktv) {
        this.linktv = linktv;
    }

}