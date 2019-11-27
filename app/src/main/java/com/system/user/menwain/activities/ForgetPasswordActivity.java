package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.system.user.menwain.R;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mRememberPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mRememberPass=findViewById(R.id.remember_pass);

        mRememberPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id==R.id.remember_pass){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }

    }
}
