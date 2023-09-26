package app.myapp.myapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import app.myapp.myapplication.APIs.Controller;
import app.myapp.myapplication.ApiModal.LoginApi;
import app.myapp.myapplication.MainActivity;
import app.myapp.myapplication.R;
import app.myapp.myapplication.databinding.ActivityLoginBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginProgressBar.setVisibility(View.GONE);
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String varEmail = binding.email.getEditText().getText().toString();
                String varPassword = binding.password.getEditText().getText().toString();
                if (!varEmail.isEmpty()) {
                    binding.email.setError("");
                    binding.email.setErrorEnabled(false);
                    if (!varPassword.isEmpty()) {
                        binding.password.setError("");
                        binding.password.setErrorEnabled(false);
                       if(!emailPattern.matches(varEmail))
                       {
                           binding.email.setError("");
                           binding.email.setErrorEnabled(false);
                           binding.loginProgressBar.setVisibility(View.VISIBLE);
                           Call<LoginApi> loginApiCall = Controller.getInstance().loginUser(varEmail, varPassword);
                           loginApiCall.enqueue(new Callback<LoginApi>() {
                               @Override
                               public void onResponse(Call<LoginApi> call, Response<LoginApi> response) {
                                   binding.loginProgressBar.setVisibility(View.GONE);
                                   if (response.code() == 200) {
                                       LoginApi data = response.body();
                                       if (data.getResult().equals("1")) {
                                           startActivity(new Intent(Login.this, Form.class));
                                           finish();
                                       } else {

                                       }
                                   } else {
                                       Log.d("LoginError", String.valueOf(response.code()));
                                       Toast.makeText(Login.this, "Email or Password is Invalid", Toast.LENGTH_SHORT).show();


                                   }

                               }

                               @Override
                               public void onFailure(Call<LoginApi> call, Throwable t) {
                                   binding.loginProgressBar.setVisibility(View.GONE);
                                   Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
                                   Log.d("LoginError", t.getLocalizedMessage().toString());
                               }
                           });
                       }
                       else {
                           binding.email.setErrorEnabled(true);
                           binding.email.setError("Invalid Email");
                       }
                    } else {
                        binding.password.setErrorEnabled(true);
                        binding.password.setError("Password Can 't be Empty");
                    }
                } else {
                    binding.email.setErrorEnabled(true);
                    binding.email.setError("Email Can 't be Empty");
                }
            }
        });
    }

}