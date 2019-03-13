package tn.igc.projectone.API;

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
    @GET("documents/search")
    Call<JsonArray> getFilterTypeMajor(@Query("type") String type,@Query("major") String major,@Query("name") String name);


    @GET("documents")
    Call<JsonArray> getAllDocs();
    @GET("corrections/byDocument/{id}")
    Call<JsonArray> getOneCorr(@Path("id") String id);
    @GET("corrections")
    Call<JsonArray> getAllCorrections();


    @FormUrlEncoded
    @POST("auth/signin")
    Call<JsonObject>basicLogin(@Field("email") String email,@Field("password") String password);
    @FormUrlEncoded
    @POST("auth/signup")
    Call<HashMap>basicsignup(@Field("email") String email,@Field("password") String password,@Field("type") String type,@Field("firstName")String firstName,@Field("lastName")String lastName,@Field("major")String major);
    @GET("majors")
    Call<JsonArray> getAllMajors();



    @GET("documents/subject/{id}")
    Call<JsonArray> getSubject_type (@Path("id") String id,@Query("type") String type);
    @GET("majors/{id}")
    Call<JsonObject> getMajor(@Path("id") String id);

}
