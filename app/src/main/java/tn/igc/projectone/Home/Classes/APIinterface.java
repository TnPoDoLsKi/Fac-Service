package tn.igc.projectone.Home.Classes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIinterface {
    @GET("subjects")
    Call<JsonArray> getAllSubjects();

    @GET("subjects/{id}")
    Call<JsonObject> getSubject(@Path("id") String id);

    @GET("documents/subject/{id}")
    Call<JsonArray> getSubject_type (@Path("id") String id,@Query("type") String type);


    @GET("majors")
    Call<JsonArray> getAllMajors();

    @GET("majors/{id}")
    Call<JsonObject> getMajor(@Path("id") String id);




}