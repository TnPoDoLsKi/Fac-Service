package tn.igc.projectone.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    Call<JsonObject> createdocument(@Field("title") String title, @Field("type") String type, @Field("filePath") String filePath, @Field("user") String user, @Field("major") String major, @Field("subject") String subject, @Field("year") String year, @Field("semestre") String semestre, @Field("profName") String profName, @Field("session") String session, @Field("corrections") Array corrections);

    @GET("search")
    Call<JsonArray> getFiltre(@Query("name") String name);

    @GET("search")
    Call<JsonArray> getFilterType(@Query("type") String type, @Query("name") String name);

    @GET("search")
    Call<JsonArray> getFilterMajor(@Query("major") String type, @Query("name") String name);

    @GET("documents/search")
    Call<JsonArray> getFilterTypeMajor(@Query("type") String type, @Query("major") String major, @Query("name") String name);


    @GET("documents")
    Call<JsonArray> getAllDocs();

    @GET("corrections/byDocument/{id}")
    Call<JsonArray> getOneCorr(@Path("id") String id);

    @GET("corrections")
    Call<JsonArray> getAllCorrections();


    @FormUrlEncoded
    @POST("auth/signin")
    Call<JsonObject> basicLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/signup")
    Call<Void> basicsignup(@Field("email") String email, @Field("password") String password, @Field("firstName") String firstName, @Field("lastName") String lastName, @Field("major") String major);

    @GET("majors")
    Call<JsonArray> getAllMajors();


    @GET("documents/bySubject/{subjectId}/byType/{type}")
    Call<JsonArray> getSubject_type(@Path("subjectId") String subjectId, @Path("type") String type);

    @GET("subjects/byMajor/{major}")
    Call<JsonArray> getMajorSujects(@Path("major") String major);

    @GET("formations")
    Call<JsonArray> getAllFormations();


    @GET("levels/byFormation/{formation}")
    Call<JsonArray> getAllLevels(@Path("formation") String id);

    @GET("majors/byLevel/{level}")
    Call<JsonArray> getMajor(@Path("level") String fId);


    @FormUrlEncoded
    @PUT("subjects/GetByMajors/")
    Call<JsonArray> getSubjectsByMajor(@Field("majors") ArrayList<String> majors);

    @FormUrlEncoded
    @PUT("users")
    Call<Void> updateUser(@Field("firstName") String fName, @Field("lastName") String lName, @Field("email") String email, @Field("major") String major, @Field("oldPassword") String oldPass, @Field("password") String pass);

    @GET("user")
    Call<JsonObject> getUserInfo();
}
