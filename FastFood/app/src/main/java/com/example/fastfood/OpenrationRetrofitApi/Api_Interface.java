package com.example.fastfood.OpenrationRetrofitApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api_Interface {

    //email_registration//

    @GET("users/email___registration.php")
    Call<ResponseBody> performEmailRegistration(
            @Query("user_name") String user_name,
            @Query("user_email") String user_email,
            @Query("user_password") String user_password
    );
    //email_login//

    @POST("users/email_login.php")
    Call<Users> performEmailLogin(
            @Query("user_email") String user_email,
            @Query("user_password") String user_password
    );

    //phone_registration///

    @GET("users/phone_registration.php")
    Call<Users> performPhoneRegistration(
            @Query("user_phone") String user_phone
    );

    //phone_login///

    @POST("users/phone_login.php")
    Call<Users> performPhoneLogin(
            @Query("user_phone") String user_phone
    );

}
