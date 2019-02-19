package tn.igc.projectone.API;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APIInterface {
    @FormUrlEncoded
    @POST("auth/signin")
    Call<JsonObject>basicLogin(@Field("email") String email,@Field("password") String password);
    @FormUrlEncoded
    @POST("auth/signup")
    Call<HashMap>basicsignup(@Field("email") String email,@Field("password") String password,@Field("type") String type,@Field("firstName")String firstName,@Field("lastName")String lastName,@Field("major")String major);
    @GET("majors")
    Call<JsonArray> getAllMajors();

}
