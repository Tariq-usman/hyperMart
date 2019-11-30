package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.AddressesAdapter;
import com.system.user.menwain.adapters.DelivieryAddressesAdapter;

public class AddressesActivity extends AppCompatActivity {
    private String [] address = {"Home","Office"};
    RecyclerView recyclerViewAddress;
    AddressesAdapter addressesAdapter;
    ImageView mBackBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);

        mBackBtn = findViewById(R.id.back);
        recyclerViewAddress = findViewById(R.id.recycler_view_addresses);
        recyclerViewAddress.setHasFixedSize(true);
        recyclerViewAddress.setLayoutManager(new LinearLayoutManager(this));

        addressesAdapter = new AddressesAdapter(address,getApplicationContext());
        recyclerViewAddress.setAdapter(addressesAdapter);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
