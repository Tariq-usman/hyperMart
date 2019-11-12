package com.system.user.menwain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mLogingBtn, mForgetPass, mCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogingBtn = findViewById(R.id.login_btn);
        mForgetPass = findViewById(R.id.forget_pass);
        mCreateAccount=findViewById(R.id.create_an_account);

        mLogingBtn.setOnClickListener(this);
        mForgetPass.setOnClickListener(this);
        mCreateAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.login_btn) {
            startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
            finish();
        } else if (id == R.id.forget_pass) {
            startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
            finish();
        }else if (id==R.id.create_an_account){
            startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            finish();
        }
    }
}
