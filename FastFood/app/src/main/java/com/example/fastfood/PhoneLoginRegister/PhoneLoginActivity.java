package com.example.fastfood.PhoneLoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.fastfood.EmailLoginRegister.EmailRegisterActivity;
import com.example.fastfood.HomeActivity;
import com.example.fastfood.MainActivity;
import com.example.fastfood.OpenrationRetrofitApi.ApiClient;
import com.example.fastfood.OpenrationRetrofitApi.Api_Interface;
import com.example.fastfood.OpenrationRetrofitApi.Users;
import com.example.fastfood.R;
import com.example.fastfood.Sessions.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneLoginActivity extends AppCompatActivity {
    EditText login_phone;
    Button btn_loginphone;
    String user_id;
    SessionManager sessionManager;

    public  static Api_Interface api_interface;
    /////////////////phone otp///////////////
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;
    /////////////////////////////////////////
    ImageView dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        /////////////status bar hide start ///////////////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /////////////status bar hide end ///////////////

        api_interface = ApiClient.getApiClient().create(Api_Interface.class);
//        mAuth = FirebaseAuth.getInstance();
        init();

        sessionManager = new SessionManager(this);


    }

    private void init() {
        login_phone = findViewById(R.id.login_phone);
        btn_loginphone = findViewById(R.id.btn_loginphone);
//        btn_loginphone2 = findViewById(R.id.btn_loginphone2);
//        login_phone_otp =findViewById(R.id.login_phone_otp);


        /////////////progressdialog///////////////////////
        dialog = findViewById(R.id.imageView_Login);
//        Glide.with(this).load(R.drawable.lazy).into(dialog);

        ////////////////////////////////////////
//        //////////////phone otp callback////////////////
//         callbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                signInwithPhoneAuthCredential(phoneAuthCredential);
//
//
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                dialog.setVisibility(View.GONE);
////                login_phone_otp.setVisibility(View.GONE);
//                login_phone.setVisibility(View.VISIBLE);
//                btn_loginphone.setVisibility(View.GONE);
////                btn_loginphone2.setVisibility(View.VISIBLE);
//
//                Toast.makeText(PhoneLoginActivity.this, "Invalid Phone Number" + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
//                mVerificationId = verificationId;
//                mResendToken = token;
//                Toast.makeText(PhoneLoginActivity.this, "Code has been sent,please check and verify", Toast.LENGTH_SHORT).show();
//
//                login_phone_otp.setVisibility(View.VISIBLE);
//                login_phone.setVisibility(View.GONE);
//                btn_loginphone.setVisibility(View.VISIBLE);
//                btn_loginphone2.setVisibility(View.GONE);
//                dialog.setVisibility(View.GONE);
//            }
//        };
//
///////////////////////////////////////////////////////////////////////////////////
//        btn_loginphone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String phonenumber = login_phone.getText().toString().trim();
//                if (TextUtils.isEmpty(phonenumber)) {
//                    login_phone.setError("Phone Number is required!");
//                }
//                if (phonenumber.length() != 10) {
//                    login_phone.setError("Phone Number should be of 10 digit !");
//                } else {
//                    dialog.setVisibility(View.VISIBLE);
//                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                            "+91" + phonenumber,// Phone number to verify
//                            60,// Timeout duration
//                            TimeUnit.SECONDS,// Unit of timeout
//                            PhoneLoginActivity.this,// Activity (for callback binding)
//                            callbacks);// OnVerificationStateChangedCallbacks
//
//                }
//            }
//        });
        btn_loginphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginPhone();
            }
        });
//        btn_loginphone2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (login_phone_otp.getText().toString().equals("")) {
//                    Toast.makeText(PhoneLoginActivity.this, "Please enter your 6 digit otp", Toast.LENGTH_SHORT).show();
//                }
//                if (login_phone_otp.getText().toString().length() != 6) {
//                    Toast.makeText(PhoneLoginActivity.this, "Invalid otp", Toast.LENGTH_SHORT).show();
//                } else {
//                    dialog.setVisibility(View.VISIBLE);
//                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, login_phone_otp.getText().toString().trim());
//                    signInwithPhoneAuthCredential(credential);
//                }
//            }
//        });
    }

    //    private void signInwithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    LoginPhone();
//                } else {
//
//                    dialog.setVisibility(View.GONE);
//                }
//            }
//        });
//    }
    private void LoginPhone() {
        String user_phone = login_phone.getText().toString().trim();
        if (TextUtils.isEmpty(user_phone)){
            login_phone.setError("Phone Number is required!");
        }else{
//            ProgressDialog dialog = new ProgressDialog(PhoneLoginActivity.this);
//            dialog.setTitle("Logging...");
//            dialog.setMessage("Please wait while we are checking your credentials");
//            dialog.show();
//            dialog.setCanceledOnTouchOutside(false);
////            Toast.makeText(PhoneLoginActivity.this,"Success",Toast.LENGTH_SHORT).show();
            Api_Interface client = ApiClient.getApiClient().create(Api_Interface.class);
            Call<Users> call = client.performPhoneLogin(login_phone.getText().toString());
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(PhoneLoginActivity.this, "Login Phone Success", Toast.LENGTH_SHORT).show();
//                    user_id = response.body().getUserId();
                        sessionManager.createSession(user_id);
                        Intent intent = new Intent(PhoneLoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        Animatoo.animateSwipeLeft(PhoneLoginActivity.this);
//                    Toast.makeText(getApplicationContext(), "dmmmm",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "error",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });



        }
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(PhoneLoginActivity.this,PhoneRegisterActivity.class);
        startActivity(intent);
        Animatoo.animateSlideUp(this);
        finish();
    }

    public void backToMainPage(View view) {
        Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSlideRight(this);
        finish();
    }
}