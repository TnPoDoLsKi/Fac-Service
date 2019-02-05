package tn.igc.projectone.filiere.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("majors")
    Call<JsonArray> getAllMajors();

    @GET("majors/{id}")
    Call<JsonObject> getOneMajor(@Path("id") String id);


}
