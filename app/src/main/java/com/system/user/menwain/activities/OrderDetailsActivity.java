package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.FavouriteAdapter;
import com.system.user.menwain.adapters.OrderDetailsAdapter;

public class OrderDetailsActivity extends AppCompatActivity {
   // private int[] products = {R.drawable.productpic, R.drawable.productdisplay, R.drawable.productpic, R.drawable.productdisplay, R.drawable.productpic, R.drawable.productdisplay};

    private String [] storesName={"Madina c carry","Metro c carry","Makro c carry","Pak c carry","Alrasheed c carry","ARY c carry",
            "Meezan c carry","Lahore c carry","ARY c carry","Meezan c carry"};
    private String [] productsName={"Cooking oil","Chicken","Meat","Butter","Eggs","Chocolate","Frouts","Carrot","Mango","Vegetables"};
    private int [] products = {R.drawable.coocking_oil,R.drawable.chikken,R.drawable.meat,R.drawable.butter,R.drawable.eggs,R.drawable.choclate,R.drawable.frouts,
            R.drawable.carrot,R.drawable.mango,R.drawable.vagitables};
    RecyclerView recyclerViewFavourite;
    OrderDetailsAdapter orderDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        recyclerViewFavourite = findViewById(R.id.recycler_view_order_details);
        recyclerViewFavourite.setHasFixedSize(true);
        recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        orderDetailsAdapter = new OrderDetailsAdapter(getApplicationContext(), products);
        recyclerViewFavourite.setAdapter(orderDetailsAdapter);
    }
}
