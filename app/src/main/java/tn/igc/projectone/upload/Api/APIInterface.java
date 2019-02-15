package tn.igc.projectone.upload.Api;

import android.content.Intent;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {
    @Multipart
    @POST("upload/")
    Call<HashMap> uploadimage(@Part MultipartBody.Part file);
}
