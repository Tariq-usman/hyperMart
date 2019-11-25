package com.system.user.menwain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.adapters.SelectedItemsFilterAdapter;
import com.system.user.menwain.fragments.CartFragment;
import com.system.user.menwain.fragments.ItemDescriptionFragment;
import com.system.user.menwain.fragments.ItemReviewsFragment;
import com.system.user.menwain.fragments.ItemSpecificationFragment;

public class ItemDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private String [] storesName={"Madina c carry","Metro c carry","Makro c carry","Pak c carry","Alrasheed c carry","ARY c carry",
            "Meezan c carry","Lahore c carry","ARY c carry","Meezan c carry"};
    private String [] productsName={"Cooking oil","Chicken","Meat","Butter","Eggs","Chocolate","Frouts","Carrot","Mango","Vegetables"};
    private int [] items = {R.drawable.coocking_oil,R.drawable.chikken,R.drawable.meat,R.drawable.butter,R.drawable.eggs,R.drawable.choclate,R.drawable.frouts,
            R.drawable.carrot,R.drawable.mango,R.drawable.vagitables};
    RecyclerView recyclerViewRelateItems;
    SelectedItemsFilterAdapter selectedItemsFilterAdapter;
    TextView mDescription, mSpecification, mReviews;
    TextView mTextView, mAddToCart;
    ImageView mCart,mClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        getSupportFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemDescriptionFragment()).commit();

        mDescription = findViewById(R.id.description_btn);
        mSpecification = findViewById(R.id.specificatin_btn);
        mReviews = findViewById(R.id.reviews_btn);
        mTextView = findViewById(R.id.text_view);
        mAddToCart = findViewById(R.id.add_to_cart);
        mCart = findViewById(R.id.cart);
        mClose = findViewById(R.id.close_back_view);

        mDescription.setOnClickListener(this);
        mSpecification.setOnClickListener(this);
        mReviews.setOnClickListener(this);
        mAddToCart.setOnClickListener(this);
        mClose.setOnClickListener(this);


        recyclerViewRelateItems = findViewById(R.id.recycler_view_related_items_item_details);
        recyclerViewRelateItems.setHasFixedSize(true);
        recyclerViewRelateItems.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                2, GridLayoutManager.VERTICAL, false));
        selectedItemsFilterAdapter = new SelectedItemsFilterAdapter(getApplicationContext(),productsName,items,storesName);
        recyclerViewRelateItems.setAdapter(selectedItemsFilterAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.description_btn) {
            getSupportFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemDescriptionFragment()).commit();
            mDescription.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_selected));
            mSpecification.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            mReviews.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
        } else if (id == R.id.specificatin_btn) {
            getSupportFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemSpecificationFragment()).commit();
            mDescription.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            mSpecification.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_selected));
            mReviews.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
        } else if (id == R.id.reviews_btn) {
            getSupportFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemReviewsFragment()).commit();
            mDescription.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            mSpecification.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            mReviews.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_selected));
        }else if (id == R.id.add_to_cart){

        }else if (id == R.id.close_back_view){
            finish();
        }
    }
}
