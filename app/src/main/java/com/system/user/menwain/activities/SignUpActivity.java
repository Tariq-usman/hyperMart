package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.system.user.menwain.responses.SignUpResponse;
import com.system.user.menwain.utils.URLs;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    TextView mLoginInstead, mRegister;
    private EditText etFname, etLname, etPhoneNo, etEmail, etPassword, etConfPass;
    private RadioButton rbMale, rbFemale;
    boolean isSignUp = false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        customProgressDialog(SignUpActivity.this);
        etFname = findViewById(R.id.et_f_name);
        etLname = findViewById(R.id.et_l_name);
        etPhoneNo = findViewById(R.id.et_phone_no);
        etEmail = findViewById(R.id.et_email_signup);
        etPassword = findViewById(R.id.et_pass_signup);
        etConfPass = findViewById(R.id.et_conf_pass_signup);
        rbMale = findViewById(R.id.rb_male);
        rbMale.setOnClickListener(this);
        rbFemale = findViewById(R.id.rb_female);
        rbFemale.setOnClickListener(this);

        mLoginInstead = findViewById(R.id.login_instead);
        mRegister = findViewById(R.id.register);
        mRegister.setOnClickListener(this);
        mLoginInstead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_male:
                rbMale.setChecked(true);
                rbFemale.setChecked(false);
                break;
            case R.id.rb_female:
                rbFemale.setChecked(true);
                rbMale.setChecked(false);
                break;
            case R.id.register:
                signUpUser();
                break;
            case R.id.login_instead:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
        }
    }

    private void signUpUser() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.user_register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SignUpResponse signUpResponse = gson.fromJson(response,SignUpResponse.class);
                Toast.makeText(SignUpActivity.this, ""+signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                isSignUp = true;
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.putExtra("is_sign_up", isSignUp);
                startActivity(intent);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
                Toast.makeText(SignUpActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("first_name", etFname.getText().toString().trim());
                map.put("last_name", etLname.getText().toString().trim());
                map.put("mobile", etPhoneNo.getText().toString().trim());
                map.put("email", etEmail.getText().toString().trim());
                map.put("password", etPassword.getText().toString().trim());
                map.put("country", "pakistan");
                map.put("age", "22");
                if (rbMale.isChecked()){
                    map.put("gender", "male");
                }else {
                    map.put("gender", "female");
                }
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
