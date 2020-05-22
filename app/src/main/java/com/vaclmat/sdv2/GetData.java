package com.vaclmat.sdv2;

import com.google.gson.JsonObject;
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
    @GET("students/")
    //Wrap the response in a Call object with the type of the expected result//
    Call<IIStudents> getAllStudents(@Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret, @Header("Accept") String aj);

    @GET("students/{id}")
    Call<IIStudent> getStudentByID(@Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret, @Header("Accept") String aj, @Path("id") String id);

    @DELETE("students/{id}")
    Call<IIStudent> deleteStudentByID(@Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret, @Header("Accept") String aj, @Path("id") String id);

    @POST("students")
    Call<Void> addStudent(@Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret, @Header("Accept") String aj, @Header("Content-Type") String aju, @Body JsonObject body);

    @PUT("students")
    Call<Void> modifyStudent(@Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret, @Header("Accept") String aj, @Header("Content-Type") String aju, @Body JsonObject body);
}
