package tn.igc.projectone.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("formations")
    Call<JsonArray> getAllFormations();


    @GET("levels/byFormation/{formation}")
    Call<JsonArray> getAllLevels(@Path("formation") String id);

    @GET("majors/byLevel/{level}")
    Call<JsonArray> getMajor(@Path("level") String fId);


    @GET("majors")
    Call<JsonArray> getAllMajors();

    @FormUrlEncoded
    @PUT("subjects/GetByMajors/")
    Call<JsonArray> getSubjectsByMajor(@Field("majors") ArrayList<String> majors);

    @FormUrlEncoded
    @PUT("users")
    Call<Void> updateUser(@Field("firstName") String fName, @Field("lastName") String lName, @Field("email") String email, @Field("major") String major, @Field("oldPassword") String oldPass, @Field("password") String pass);

    @GET("user")
    Call<JsonObject> getUserInfo();


}






































