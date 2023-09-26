package app.myapp.myapplication.APIs;

import app.myapp.myapplication.ApiModal.LoginApi;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login")
    Call<LoginApi> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

}
