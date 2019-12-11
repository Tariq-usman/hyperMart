package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.ExploreItemsGridAdapter;
import com.system.user.menwain.adapters.ExploreItemsListAdapter;
import com.system.user.menwain.adapters.ExploreShopItemsGridAdapter;
import com.system.user.menwain.adapters.ExploreShopItemsListAdapter;

public class AllItemsActivity extends AppCompatActivity implements View.OnClickListener {
    private String[] productsName = {"Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallaxy S8", "Samsung", "BlockBuster", "Gallexy M30",
            "Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallexy M30s", "Samsung s4"};
    private int[] products = {R.drawable.p, R.drawable.pict, R.drawable.pictu, R.drawable.picturep,
            R.drawable.picturepi, R.drawable.picturepic, R.drawable.p, R.drawable.pict, R.drawable.pictu,
            R.drawable.picturep, R.drawable.picturepi, R.drawable.picturepic};


    private String[] productsName1 = {"Cooking oil", "Chicken", "Meat", "Butter", "Eggs", "Chocolate", "Frouts", "Carrot", "Mango", "Vegetables"};
    private int[] items = {R.drawable.coocking_oil, R.drawable.chikken, R.drawable.meat, R.drawable.butter, R.drawable.eggs, R.drawable.choclate, R.drawable.frouts,
            R.drawable.carrot, R.drawable.mango, R.drawable.vagitables};

    private RecyclerView recyclerViewAllitem;
    private LinearLayoutManager linearLayoutManager;
    private ImageView ivGridListView, ivBack;
    private boolean isList = false;
    private Intent intent;
    private int val, val1, val2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        ivGridListView = findViewById(R.id.iv_grid_list_view);
        ivGridListView.setOnClickListener(this);

        intent = getIntent();
        val = intent.getIntExtra("explore_shop", 0);
        val1 = intent.getIntExtra("explore", 0);
        val2 = intent.getIntExtra("shop", 0);
        Log.i("val", String.valueOf(val));
        Log.i("val1", String.valueOf(val1));
        recyclerViewAllitem = findViewById(R.id.recycler_view_grid_items);
        if (val == 1) {
            recyclerViewAllitem.setHasFixedSize(true);
            recyclerViewAllitem.setLayoutManager(new GridLayoutManager(AllItemsActivity.this, 3));
            recyclerViewAllitem.setAdapter(new ExploreShopItemsGridAdapter(getApplicationContext(), productsName, products));
        } else if (val1 == 2) {
            recyclerViewAllitem.setLayoutManager(new GridLayoutManager(AllItemsActivity.this, 3));
            recyclerViewAllitem.setAdapter(new ExploreItemsGridAdapter(getApplicationContext(), productsName1, items));
        } else if (val2 == 3) {
            recyclerViewAllitem.setLayoutManager(new GridLayoutManager(AllItemsActivity.this, 3));
            recyclerViewAllitem.setAdapter(new ExploreShopItemsGridAdapter(getApplicationContext(), productsName, products));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_grid_list_view:
                if (val == 1) {
                    if (isList == false) {
                        isList = true;
                        ivGridListView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreShopItemsListAdapter(getApplicationContext(), productsName, products));

                    } else {
                        isList = false;
                        ivGridListView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreShopItemsGridAdapter(getApplicationContext(), productsName, products));
                    }
                } else if (val1 == 2) {
                    if (isList == false) {
                        isList = true;
                        ivGridListView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreItemsListAdapter(getApplicationContext(), productsName1, items));

                    } else {
                        isList = false;
                        ivGridListView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreItemsGridAdapter(getApplicationContext(), productsName1, items));
                    }
                } else if (val2 == 3) {
                    if (isList == false) {
                        isList = true;
                        ivGridListView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreShopItemsListAdapter(getApplicationContext(), productsName, products));

                    } else {
                        isList = false;
                        ivGridListView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreShopItemsGridAdapter(getApplicationContext(), productsName, products));
                    }
                }
                break;

        }

    }
}
