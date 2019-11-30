package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;

public class RateUsActivity extends AppCompatActivity {

    TextView mConfirmBtn, mTitle;
    ImageView mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        mConfirmBtn = findViewById(R.id.submit);
        mTitle = findViewById(R.id.title_view);
        mBackBtn = findViewById(R.id.close_back_view);

        mTitle.setText("Rate Us");
        mBackBtn.setImageResource(R.drawable.ic_backwhite);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
