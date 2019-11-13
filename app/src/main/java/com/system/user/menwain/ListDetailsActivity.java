package com.system.user.menwain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.system.user.menwain.adapters.ListDetailsAdapter;

public class ListDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private int[] products = {R.drawable.productpic, R.drawable.productdisplay, R.drawable.productpic, R.drawable.productdisplay};
    RecyclerView recyclerViewListDetails;
    ListDetailsAdapter listDetailsAdapter;
    FloatingActionButton floatingActionButton;
    TextView mConfirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);

        floatingActionButton = findViewById(R.id.action_btn_list_details);
        mConfirmBtn = findViewById(R.id.confirm_btn_list_details);

        floatingActionButton.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);

        recyclerViewListDetails = findViewById(R.id.recycler_view_list_details);
        recyclerViewListDetails.setHasFixedSize(true);
        recyclerViewListDetails.setLayoutManager(new GridLayoutManager(this,2,RecyclerView.VERTICAL,false));
        listDetailsAdapter = new ListDetailsAdapter(getApplicationContext(),products);
        recyclerViewListDetails.setAdapter(listDetailsAdapter);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.confirm_btn_list_details){
         finish();
        }

    }
}
