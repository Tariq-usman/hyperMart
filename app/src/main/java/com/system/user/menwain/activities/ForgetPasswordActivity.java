package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.system.user.menwain.responses.PassRecoverThroughNum;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mRememberPass, mSend;
    private EditText etPhnNoEmail;
    private String phoneNoEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        customProgressDialog(ForgetPasswordActivity.this);
        etPhnNoEmail = findViewById(R.id.et_phone_no_email);
        mRememberPass = findViewById(R.id.remember_pass);
        mRememberPass.setOnClickListener(this);
        mSend = findViewById(R.id.tv_send);
        mSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                phoneNoEmail = etPhnNoEmail.getText().toString().trim();
                if (phoneNoEmail.matches(Utils.phone_n_pattern)) {
                    passRecoverThroughNum();
                } else {
                    Toast.makeText(this, "No Num", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.remember_pass:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
        }

    }

    private void passRecoverThroughNum() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.pass_recover_through_num_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PassRecoverThroughNum throughNum = gson.fromJson(response, PassRecoverThroughNum.class);
                Toast.makeText(ForgetPasswordActivity.this, "" + throughNum.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgetPasswordActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("mobile", phoneNoEmail);
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
