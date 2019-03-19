package tn.igc.projectone.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import java.util.HashMap;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @Multipart
    @POST("documents/upload/")
    Call<JsonArray> uploadimage(@Part ArrayList<MultipartBody.Part> files);

    @Multipart
    @POST("documents/upload/")
    Call<JsonArray> uploadimage1(@Part MultipartBody.Part files);

    @FormUrlEncoded
    @POST("documents")
    Call<JsonObject> createdocument(@Field("type")String type, @Field("filesStaging")ArrayList<String> filesStaging, @Field("subject")String subject,@Field("year")String year, @Field("description")String description, @Field("session") String session);

    @FormUrlEncoded
    @POST("corrections")
    Call<JsonObject> createcorrection(@Field("filesStaging")ArrayList<String> filesStaging,@Field("document")String document);

    @GET("search")
    Call<JsonArray> getFiltre(@Query("name") String name);
    @GET("search")
    Call<JsonArray> getFilterType(@Query("type") String type,@Query("name") String name);
    @GET("search")
    Call<JsonArray> getFilterMajor(@Query("major") String type,@Query("name") String name);
    @GET("search")
    Call<JsonArray> getFilterTypeMajor(@Query("type") String type,@Query("major") String major,@Query("name") String name);


    @GET("documents")
    Call<JsonArray> getAllDocs();
    @GET("documents/corrections/{id}")
    Call<JsonArray> getOneCorr(@Path("id") String id);
    @GET("corrections")
    Call<JsonArray> getAllCorrections();


    @FormUrlEncoded
    @POST("auth/signin")
    Call<JsonObject>basicLogin(@Field("email") String email,@Field("password") String password);
    @GET("majors")
    Call<JsonArray> getAllMajors();
    @FormUrlEncoded
    @POST("auth/signup")
    Call<Void>basicsignup(@Field("email") String email,@Field("password") String password,@Field("firstName")String firstName,@Field("lastName")String lastName,@Field("major")String major);



    @GET("documents/subject/{id}")
    Call<JsonArray> getSubject_type (@Path("id") String id,@Query("type") String type);
    @GET("majors/{id}")
    Call<JsonObject> getMajor(@Path("id") String id);

}
