package com.system.user.menwain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mLoginInstead,mRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mLoginInstead=findViewById(R.id.login_instead);
        mRegister=findViewById(R.id.register);

        mRegister.setOnClickListener(this);
        mLoginInstead.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id==R.id.register){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }else
        if (id==R.id.login_instead){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
    }
}
