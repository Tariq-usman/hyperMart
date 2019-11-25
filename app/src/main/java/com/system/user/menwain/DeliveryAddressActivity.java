package com.system.user.menwain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.adapters.DelivieryAddressesAdapter;

public class DeliveryAddressActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTitleView, mConfirmBtn;
    ImageView mBackView;
    private String [] address = {"Home","Office"};
    RecyclerView recyclerViewAddress;
    DelivieryAddressesAdapter delivieryAddressesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivey_address);

        mTitleView = findViewById(R.id.title_view);
        mBackView = findViewById(R.id.close_back_view);
        mConfirmBtn = findViewById(R.id.confirm_btn);

        mBackView.setImageResource(R.drawable.ic_backwhite);
        mTitleView.setText("Deliver to");
        mConfirmBtn.setOnClickListener(this);
        mBackView.setOnClickListener(this);

        recyclerViewAddress = findViewById(R.id.recycler_view_delivery_address);
        recyclerViewAddress.setHasFixedSize(true);
        recyclerViewAddress.setLayoutManager(new LinearLayoutManager(this));

        delivieryAddressesAdapter = new DelivieryAddressesAdapter(address,getApplicationContext());
        recyclerViewAddress.setAdapter(delivieryAddressesAdapter);

    }

    @Override
    public void onClick(View view) {
        int id= view.getId();

        if (id==R.id.confirm_btn){
            startActivity(new Intent(getApplicationContext(),ItemsAvailabilityStoresActivity.class));
            finish();
        }else if (id == R.id.close_back_view){
            finish();
        }

    }
}
