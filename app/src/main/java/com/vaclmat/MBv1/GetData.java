package com.vaclmat.MBv1;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GetData {


    //Specify the request type and pass the relative URL//
    @GET("mo/v1/vip/{un}")
    //Wrap the response in a Call object with the type of the expected result//
    Call<List<VidRec>> getVid(@Header("Authorization") String tokenwB, @Header("Accept") String aj, @Header("Content-Type") String aju, @Path("un") String id);

    @GET("mo/v1/vn/{vid}")
    Call<List<VideoRec>> getVn(@Header("Authorization") String tokenwB, @Header("Accept") String aj, @Header("Content-Type") String aju, @Path("vid") String vid_id);

    //Specify the request type and pass the relative URL//
    @POST("jwt-auth/v1/token")
    //Wrap the response in a Call object with the type of the expected result//
    Call<Ud> getToken(@Header("Content-Type") String aju, @Body JsonObject body);

    @POST("students")
    Call<Void> addStudent(@Header("Accept") String aj, @Header("Content-Type") String aju, @Body JsonObject body);


}
