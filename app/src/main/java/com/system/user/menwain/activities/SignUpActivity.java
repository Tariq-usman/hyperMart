package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.DeliveryAddressFragment;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mLoginInstead,mRegister;
    boolean isSignUp = false;
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
            isSignUp = true;
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            intent.putExtra("is_sign_up", isSignUp);
            startActivity(intent);
        }else
        if (id==R.id.login_instead){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
    }
}
