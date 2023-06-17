package com.example.fastfood.EmailLoginRegister;

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
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fastfood.HomeActivity;
import com.example.fastfood.MainActivity;
import com.example.fastfood.OpenrationRetrofitApi.ApiClient;
import com.example.fastfood.OpenrationRetrofitApi.Api_Interface;
import com.example.fastfood.OpenrationRetrofitApi.Users;
import com.example.fastfood.R;
import com.example.fastfood.Sessions.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.api.Authentication;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailLoginActivity extends AppCompatActivity {
    EditText login_email,login_password;
    Button btn_login_email;
    FirebaseAuth mAuth;


    public static Api_Interface api_interface;

    String user_id;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        /////////////status bar hide start ///////////////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /////////////status bar hide end ///////////////

        api_interface = ApiClient.getApiClient().create(Api_Interface.class);

        sessionManager = new SessionManager(this);

        init();
    }

    private void init() {
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        btn_login_email = findViewById(R.id.btn_login_email);
        btn_login_email.setOnClickListener(v -> {
            Login();
        });
//        btn_login_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Login();
//            }
//        });
    }

    private void Login() {

        String user_email = login_email.getText().toString().trim();
        String user_password = login_password.getText().toString().trim();

        if(TextUtils.isEmpty(user_email)){
            login_email.setError("Email is required!");
        }else if(TextUtils.isEmpty(user_password)){
            login_password.setError("Password is required!");
        }else{
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(user_email, user_password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                startActivity(new Intent(EmailLoginActivity.this,HomeActivity.class));
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(EmailLoginActivity.this,"Success",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EmailLoginActivity.this,"loss",Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user.

                            }
                        }
                    });
//            ProgressDialog dialog = new ProgressDialog(this);
//            dialog.setTitle("Logging...");
//            dialog.setMessage("Please wait while we are checking your credentials");
//            dialog.show();
//            dialog.setCanceledOnTouchOutside(false);
//            Toast.makeText(EmailLoginActivity.this,"Success",Toast.LENGTH_SHORT).show();

//            Api_Interface client = ApiClient.getApiClient().create(Api_Interface.class);
//            Call<Users> call = client.performEmailLogin(user_email,user_password);
//            call.enqueue(new Callback<Users>() {
//                @Override
//                public void onResponse(Call<Users> call, Response<Users> response) {
//                    if (response.isSuccessful()){
//
//                        user_id = response.body().getUserId();
//                        Log.e("TEST", "Test"+ user_id);
//                        sessionManager.createSession(user_id);
//                        Intent intent = new Intent(EmailLoginActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Animatoo.animateSwipeLeft(EmailLoginActivity.this);
////                    Toast.makeText(getApplicationContext(), "dmmmm",Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(getApplicationContext(), "error",Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Users> call, Throwable t) {
//                    Toast.makeText(EmailLoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//
//                }
//            });


        }
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(EmailLoginActivity.this,EmailRegisterActivity.class);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);
        finish();
    }

    public void backToMainPage(View view) {
        Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSlideRight(this);
        finish();
    }
}