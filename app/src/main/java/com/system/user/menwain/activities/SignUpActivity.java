package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.R;
import com.system.user.menwain.custom_languages.BaseActivity;
import com.system.user.menwain.responses.SignUpResponse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    TextView mLoginInstead, mRegister;
    private EditText etFname, etLname, etPhoneNo, etEmail, etPassword, etConfPass, etAge, etCountry;
    private RadioButton rbMale, rbFemale;
    boolean isSignUp = false;
    private Dialog progressDialog;
    private String email, pass, cPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       progressDialog =Utils.dialog(SignUpActivity.this);
        etFname = findViewById(R.id.et_f_name);
        etLname = findViewById(R.id.et_l_name);
        etPhoneNo = findViewById(R.id.et_phone_no);
        etEmail = findViewById(R.id.et_email_signup);
        etPassword = findViewById(R.id.et_pass_signup);
        etConfPass = findViewById(R.id.et_conf_pass_signup);
        etAge = findViewById(R.id.et_age_signup);
        etCountry = findViewById(R.id.et_country_signup);
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
                email = etEmail.getText().toString().trim();
                pass = etPassword.getText().toString().trim();
                cPass = etConfPass.getText().toString().trim();
                if (etFname.getText().toString().isEmpty()) {
                    etFname.setError("Enter first name");
                } else if (etLname.getText().toString().isEmpty()) {
                    etLname.setError("Enter last name");
                } else if (etPhoneNo.getText().toString().isEmpty()) {
                    etPhoneNo.setError("Enter phone no");
                } else if (!email.matches(Utils.emailPattern)) {
                    etEmail.setError("Please enter valid email");
                } else if (etAge.getText().toString().isEmpty()) {
                    etAge.setError("Enter age");
                } else if (etCountry.getText().toString().isEmpty()) {
                    etCountry.setError("Enter country");
                } else if (pass.length() < 8 && !Utils.isValidPassword(pass)) {
                    etPassword.setError("Not Valid Password");
                } else if (!cPass.equalsIgnoreCase(etConfPass.getText().toString().trim())) {
                    etConfPass.setError("Password Not matching");
                } else {
                    signUpUser();
                }
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
                try {
                    SignUpResponse signUpResponse = gson.fromJson(response, SignUpResponse.class);
                    Toast.makeText(SignUpActivity.this, "" + signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    isSignUp = true;
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.putExtra("is_sign_up", isSignUp);
                    startActivity(intent);
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                if (error != null && error.networkResponse != null && error.networkResponse.data != null) {
                    String str = new String(error.networkResponse.data);
                    Log.e("Error : ", str);
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        String strError = jsonObject.getString("message");
                        Toast.makeText(SignUpActivity.this, "" + strError, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(SignUpActivity.this, getString(R.string.network_timeout), Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(SignUpActivity.this, getString(R.string.authentication_error), Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(SignUpActivity.this, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                            Toast.makeText(SignUpActivity.this, getString(R.string.no_network_found), Toast.LENGTH_LONG).show();
                        } else {
                        }
                        progressDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }  progressDialog.dismiss();
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
                map.put("country", etCountry.getText().toString().trim());
                map.put("age", etAge.getText().toString().trim());
                if (rbMale.isChecked()) {
                    map.put("gender", "male");
                } else {
                    map.put("gender", "female");
                }
                return map;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(50000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
