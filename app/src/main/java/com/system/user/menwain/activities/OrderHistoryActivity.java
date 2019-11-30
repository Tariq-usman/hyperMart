package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.OrderHistoryAdapter;

public class OrderHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    String [] orderNumbers = {"25462","55214","14532","455132","32556","54268"};
    RecyclerView recyclerViewOrderHistory;
    OrderHistoryAdapter orderHistoryAdapter;
    TextView mTitle;
    ImageView mBackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        mTitle = findViewById(R.id.title_view);
        mBackBtn = findViewById(R.id.close_back_view);
        mBackBtn.setOnClickListener(this);

        mTitle.setText("Order History");
        mBackBtn.setImageResource(R.drawable.ic_backwhite);

        recyclerViewOrderHistory = findViewById(R.id.recycler_view_order_history);
        recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        orderHistoryAdapter = new OrderHistoryAdapter(getApplicationContext(),orderNumbers);
        recyclerViewOrderHistory.setAdapter(orderHistoryAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (view.getId()){
            case R.id.close_back_view:
                finish();
                break;
        }
    }
}
