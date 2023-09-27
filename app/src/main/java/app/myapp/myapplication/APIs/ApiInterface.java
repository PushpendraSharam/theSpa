package app.myapp.myapplication.APIs;

import app.myapp.myapplication.ApiModal.LoginApi;
import app.myapp.myapplication.Modals.BookingResponse;
import app.myapp.myapplication.Modals.CouponResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login")
    Call<LoginApi> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @Multipart
    @POST("booking")
    Call<ResponseBody> fillBookingForm(
            @Part MultipartBody.Part captured_image,
            @Part MultipartBody.Part signatureData,
            @Part("fname") RequestBody fname,
            @Part("lname") RequestBody lname,
            @Part("phone") RequestBody phone,
            @Part("medication") RequestBody medication,
            @Part("source_appointment") RequestBody source_appointment,
            @Part("question") RequestBody question,
            @Part("mtype_check") RequestBody mtype_check,
            @Part("new_price") RequestBody new_price,
            @Part("mtype_time") RequestBody mtype_time,
            @Part("rooms") RequestBody rooms,
            @Part("thearapy_name") RequestBody thearapy_name,
            @Part("intime") RequestBody intime,
            @Part("intime_select") RequestBody intime_select,
            @Part("outime") RequestBody outime,
            @Part("outTime_select") RequestBody outTime_select,
            @Part("payment_mode") RequestBody payment_mode,
            @Part("gender_selection") RequestBody gender_selection,
            @Part("date") RequestBody date,
            @Part("discount_price") RequestBody discount_price


    );

    @FormUrlEncoded
    @POST("coupon")
    Call<CouponResponse> checkCoupon(
            @Field("couponCode") String couponCode

    );
}
