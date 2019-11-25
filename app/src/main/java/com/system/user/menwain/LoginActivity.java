package com.system.user.menwain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mLogingBtn, mForgetPass, mCreateAccount;
    EditText mPhoneNo, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        } else if (id == R.id.forget_pass) {
            startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
            finish();
        } else if (id == R.id.create_an_account) {
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            finish();
        }
    }
}
