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

public interface APIInterface {
    @Multipart
    @POST("documents/upload/")
    Call<JsonArray> uploadimage(@Part ArrayList<MultipartBody.Part> files);

    @Multipart
    @POST("documents/upload/")
    Call<JsonArray> uploadimage1(@Part MultipartBody.Part files);

    @FormUrlEncoded
    @POST("documents")
    Call<JsonObject> createdocument(@Field("title")String title, @Field("type")String type, @Field("filePath")String filePath, @Field("user")String user, @Field("major") String major, @Field("subject")String subject, @Field("year")String year, @Field("semestre")String semestre, @Field("profName")String profName, @Field("session")String session, @Field("corrections") Array corrections);


}
