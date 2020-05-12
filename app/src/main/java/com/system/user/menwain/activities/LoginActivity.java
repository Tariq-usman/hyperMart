package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.R;
import com.system.user.menwain.custom_languages.BaseActivity;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.LogInResponse;
import com.system.user.menwain.utils.URLs;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    TextView mLogingBtn, mForgetPass, mCreateAccount;
    EditText mPhoneNo, mPassword;
    private Preferences prefrences;
    ProgressDialog progressDialog;
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefrences = new Preferences(this);
        customProgressDialog(LoginActivity.this);
        mLogingBtn = findViewById(R.id.login_btn);
        mForgetPass = findViewById(R.id.forget_pass);
        mCreateAccount = findViewById(R.id.create_an_account);
        mPhoneNo = findViewById(R.id.et_phone_no);
        mPassword = findViewById(R.id.et_pass);

        mLogingBtn.setOnClickListener(this);
        mForgetPass.setOnClickListener(this);
        mCreateAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.login_btn) {
            if (mPhoneNo.getText().toString().trim().isEmpty()) {
                mPhoneNo.setError("Enter Phn no..");
            } else if (mPassword.getText().toString().trim().length() < 6) {
                mPassword.setError("Password is too week..");
            } else {
                userLogIn();
            }
        } else if (id == R.id.forget_pass) {
            startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
            finish();
        } else if (id == R.id.create_an_account) {
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            finish();
        }
    }

    private void userLogIn() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.user_log_in_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                isLogin = true;
                LogInResponse logInResponse = gson.fromJson(response, LogInResponse.class);
                prefrences.setToken(logInResponse.getToken());
                Toast.makeText(LoginActivity.this, "Login Successfully..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("isLogin", isLogin);
                startActivity(intent);
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("login_error", error.toString());
                Toast.makeText(LoginActivity.this, "Login Credentials were wrong", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("mobile", mPhoneNo.getText().toString().trim());
                map.put("password", mPassword.getText().toString().trim());
                return map;
            }
        };
        requestQueue.add(request);
    }
    public void customProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Fetching max value
        progressDialog.getMax();
    }
}
