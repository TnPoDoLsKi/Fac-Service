package tn.igc.projectone.Home.Classes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIinterface {
    @GET("subjects")
    Call<JsonArray> getAllSubjects();

    @GET("subjects/{id}")
    Call<JsonObject> getSubject(@Path("id") String id);

    @GET("majors")
    Call<JsonArray> getAllMajors();

    @GET("majors/{id}")
    Call<JsonObject> getMajor(@Path("id") String id);


}