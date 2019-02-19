package tn.igc.projectone;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("search")
    Call<JsonArray> getFiltre(@Query("name") String name);
    @GET("search")
    Call<JsonArray> getFilterType(@Query("type") String type,@Query("name") String name);
    @GET("search")
    Call<JsonArray> getFilterMajor(@Query("major") String type,@Query("name") String name);
    @GET("search")
    Call<JsonArray> getFilterTypeMajor(@Query("type") String type,@Query("major") String major,@Query("name") String name);

    @GET("tasks")
    Call<JSONObject> getAllTasks();
    //---------------------------------------------------------------------------------------------------------------
    @GET("documents")
    Call<JsonArray> getAllDocs();

    @GET("documents/corrections/{id}")
    Call<JsonArray> getOneCorr(@Path("id") String id);

    @GET("corrections")
    Call<JsonArray> getAllCorrections();



    @GET("task/{id}")
    Call<JsonObject> getOneTask(@Path("id") String id);

    @FormUrlEncoded
    @POST("task")
    Call<HashMap> createTask(@Field("message") String msg, @Field("date") String date);

    @FormUrlEncoded
    @PUT("task/{id}")
    Call<HashMap> updateTask(@Path("id") String idd, @Field("message") String message);

    @DELETE("task/{id}")
    Call<HashMap> deleteTask(@Path("id") String id);
}
