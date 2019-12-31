package com.system.user.menwain.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;

import com.system.user.menwain.Prefrences;
import com.system.user.menwain.custom_languages.BaseActivity;
import com.system.user.menwain.custom_languages.LocaleManager;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.cart.AvailNotAvailItemsListsFragment;
import com.system.user.menwain.fragments.cart.ItemsAvailabilityStoresFragment;
import com.system.user.menwain.fragments.my_list.AllListsFragment;
import com.system.user.menwain.fragments.cart.CartFragment;
import com.system.user.menwain.fragments.cart.DeliveryAddressFragment;
import com.system.user.menwain.fragments.category.CategoryStoresFragment;
import com.system.user.menwain.fragments.home.HomeFragment;
import com.system.user.menwain.fragments.more.MoreFragment;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public AppBarConfiguration mAppBarConfiguration;
    private ImageView mCart, mFavourite, mHome, mCategory, mMore, ivBack, ivListGridView, ivMenu;
    private LinearLayout home_layout, category_layout, my_list_layout, more_layout;
    private RelativeLayout cart_layout;
    private CartViewModel cartViewModel;
    private TextView totalCartQuantity, mActionBarTitle, tvHome, tvCategory, tvCart, tvMore, tvFavourite;
    private String langauge;
    SharedPreferences preferences;
    SharedPreferences.Editor editor, editor1;
    boolean isLogin, isSignUp = false;
    private SharedPreferences fragment_status_pref;
    private int frag_status;
    Prefrences prefrences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        prefrences = new Prefrences(this);
        //fragment_status_pref = getSharedPreferences("fragment_status", MODE_PRIVATE);
        frag_status = prefrences.getFragStatus();
//        frag_status = fragment_status_pref.getInt("frag_status", 0);
        Intent intent = getIntent();
        Log.e("IS LOGIN", String.valueOf(isLogin));
        if (intent != null) {
            Log.e("IS LOGIN", String.valueOf(isLogin));
            isLogin = intent.getBooleanExtra("isLogin", false);
            isSignUp = intent.getBooleanExtra("is_sign_up", false);
        }
        preferences = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        langauge = preferences.getString("my_lang", "");

        if (langauge.isEmpty()) {
            showSelectLangaugeDialog();
        }


        initiateViews();

        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);

        cartViewModel.getTotalItemQuantity().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                if (integer != null) {
                    totalCartQuantity.setText(integer + "");
                } else {
                    totalCartQuantity.setText(0 + "");
                }
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        mHome.setImageResource(R.drawable.ic_houseblue);
        tvHome.setTextColor(Color.parseColor("#00c1bd"));
//        mActionBarTitle.setText("Home");

        if (savedInstanceState == null) {
            if (isLogin == true) {
                mHome.setImageResource(R.drawable.ic_housewhite);
                tvHome.setTextColor(Color.parseColor("#FFFFFF"));
                mCategory.setImageResource(R.drawable.ic_searchwhite);
                tvCategory.setTextColor(Color.parseColor("#FFFFFF"));
                mFavourite.setImageResource(R.drawable.ic_likewhite);
                tvFavourite.setTextColor(Color.parseColor("#FFFFFF"));
                mCart.setImageResource(R.drawable.ic_cart_blue);
                tvCart.setTextColor(Color.parseColor("#00c1bd"));
                mMore.setImageResource(R.drawable.ic_morewhite);
                tvMore.setTextColor(Color.parseColor("#FFFFFF"));
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DeliveryAddressFragment(), "del").commit();
            } else if (isSignUp == true) {
                mHome.setImageResource(R.drawable.ic_housewhite);
                tvHome.setTextColor(Color.parseColor("#FFFFFF"));
                mCategory.setImageResource(R.drawable.ic_searchwhite);
                tvCategory.setTextColor(Color.parseColor("#FFFFFF"));
                mFavourite.setImageResource(R.drawable.ic_likewhite);
                tvFavourite.setTextColor(Color.parseColor("#FFFFFF"));
                mCart.setImageResource(R.drawable.ic_cart_blue);
                tvCart.setTextColor(Color.parseColor("#00c1bd"));
                mMore.setImageResource(R.drawable.ic_morewhite);
                tvMore.setTextColor(Color.parseColor("#FFFFFF"));
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DeliveryAddressFragment(), "del").commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "Home").commit();
            }

        }
    }

    private void initiateViews() {

        ivListGridView = findViewById(R.id.iv_grid_list_view);

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        home_layout = findViewById(R.id.home_layout);
        mHome = findViewById(R.id.home_view);
        tvHome = findViewById(R.id.tv_home_view);

        category_layout = findViewById(R.id.category_layout);
        mCategory = findViewById(R.id.category_view);
        tvCategory = findViewById(R.id.tv_category_view);

        cart_layout = findViewById(R.id.cart_layout);
        mCart = findViewById(R.id.cart);
        tvCart = findViewById(R.id.tv_cart);

        my_list_layout = findViewById(R.id.my_list_layout);
        mFavourite = findViewById(R.id.favourite_view);
        tvFavourite = findViewById(R.id.tv_favourite_view);

        mMore = findViewById(R.id.more);
        tvMore = findViewById(R.id.tv_more);
        more_layout = findViewById(R.id.more_layout);

        totalCartQuantity = findViewById(R.id.total_cart_quantity);
        // mActionBarTitle = findViewById(R.id.toolbar_title);

        more_layout.setOnClickListener(this);
        home_layout.setOnClickListener(this);
        category_layout.setOnClickListener(this);
        cart_layout.setOnClickListener(this);
        my_list_layout.setOnClickListener(this);
    }

    private void showSelectLangaugeDialog() {
        final String[] langaugesList = {"English", "عربى"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Language");
        builder.setSingleChoiceItems(langaugesList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i) {
                    case 0:
                        editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
                        editor.putString("my_lang", String.valueOf(i));
                        editor.apply();
                        setNewLocale(MainActivity.this, LocaleManager.ENGLISH);
                        recreate();
                        break;
                    case 1:
                        editor1 = getSharedPreferences("settings", MODE_PRIVATE).edit();
                        editor1.putString("my_lang", String.valueOf(i));
                        editor1.apply();
                        setNewLocale(MainActivity.this, LocaleManager.ARABIC);
                        recreate();
                        break;
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setNewLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = mContext.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.home_layout) {
            //  mActionBarTitle.setText("Home");
            ivListGridView.setVisibility(View.INVISIBLE);
            mHome.setImageResource(R.drawable.ic_houseblue);
            ivBack.setVisibility(View.INVISIBLE);
            tvHome.setTextColor(Color.parseColor("#00c1bd"));
            mCategory.setImageResource(R.drawable.ic_searchwhite);
            tvCategory.setTextColor(Color.parseColor("#004040"));
            mFavourite.setImageResource(R.drawable.ic_likewhite);
            tvFavourite.setTextColor(Color.parseColor("#004040"));
            mCart.setImageResource(R.drawable.ic_cart_white);
            tvCart.setTextColor(Color.parseColor("#004040"));
            mMore.setImageResource(R.drawable.ic_morewhite);
            tvMore.setTextColor(Color.parseColor("#004040"));
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "Home").commit();
        } else if (id == R.id.category_layout) {
            ivBack.setVisibility(View.INVISIBLE);
            ivListGridView.setVisibility(View.INVISIBLE);
            mHome.setImageResource(R.drawable.ic_housewhite);
            tvHome.setTextColor(Color.parseColor("#004040"));
            mCategory.setImageResource(R.drawable.ic_searchblue);
            tvCategory.setTextColor(Color.parseColor("#00c1bd"));
            mFavourite.setImageResource(R.drawable.ic_likewhite);
            tvFavourite.setTextColor(Color.parseColor("#004040"));
            mCart.setImageResource(R.drawable.ic_cart_white);
            tvCart.setTextColor(Color.parseColor("#004040"));
            mMore.setImageResource(R.drawable.ic_morewhite);
            tvMore.setTextColor(Color.parseColor("#004040"));
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CategoryStoresFragment()).addToBackStack(null).commit();

        } else if (id == R.id.cart_layout) {
            frag_status = prefrences.getFragStatus();
            ivBack.setVisibility(View.INVISIBLE);
            ivListGridView.setVisibility(View.INVISIBLE);
            mHome.setImageResource(R.drawable.ic_housewhite);
            tvHome.setTextColor(Color.parseColor("#004040"));
            mCategory.setImageResource(R.drawable.ic_searchwhite);
            tvCategory.setTextColor(Color.parseColor("#004040"));
            mFavourite.setImageResource(R.drawable.ic_likewhite);
            tvFavourite.setTextColor(Color.parseColor("#004040"));
            mCart.setImageResource(R.drawable.ic_cart_blue);
            tvCart.setTextColor(Color.parseColor("#00c1bd"));
            mMore.setImageResource(R.drawable.ic_morewhite);
            tvMore.setTextColor(Color.parseColor("#004040"));
            if (frag_status == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).addToBackStack(null).commit();
            } else if (frag_status == 1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DeliveryAddressFragment()).addToBackStack(null).commit();
            } else if (frag_status == 2) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ItemsAvailabilityStoresFragment()).addToBackStack(null).commit();
            } else if (frag_status == 3){
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AvailNotAvailItemsListsFragment()).addToBackStack(null).commit();
            }
        } else if (id == R.id.my_list_layout) {
            ivBack.setVisibility(View.INVISIBLE);
            ivListGridView.setVisibility(View.INVISIBLE);
            mHome.setImageResource(R.drawable.ic_housewhite);
            tvHome.setTextColor(Color.parseColor("#004040"));
            mCategory.setImageResource(R.drawable.ic_searchwhite);
            tvCategory.setTextColor(Color.parseColor("#004040"));
            mFavourite.setImageResource(R.drawable.ic_likeblue);
            tvFavourite.setTextColor(Color.parseColor("#00c1bd"));
            mCart.setImageResource(R.drawable.ic_cart_white);
            tvCart.setTextColor(Color.parseColor("#004040"));
            mMore.setImageResource(R.drawable.ic_morewhite);
            tvMore.setTextColor(Color.parseColor("#004040"));
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AllListsFragment()).commit();
        } else if (id == R.id.more_layout) {
            ivBack.setVisibility(View.INVISIBLE);
            mHome.setImageResource(R.drawable.ic_housewhite);
            tvHome.setTextColor(Color.parseColor("#004040"));
            mCategory.setImageResource(R.drawable.ic_searchwhite);
            tvCategory.setTextColor(Color.parseColor("#004040"));
            mFavourite.setImageResource(R.drawable.ic_likewhite);
            tvFavourite.setTextColor(Color.parseColor("#004040"));
            mCart.setImageResource(R.drawable.ic_cart_white);
            tvCart.setTextColor(Color.parseColor("#004040"));
            mMore.setImageResource(R.drawable.ic_moreblue);
            tvMore.setTextColor(Color.parseColor("#00c1bd"));
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new MoreFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("Home");
        if (fragment != null && fragment.isVisible()) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
        } else {
            setFragment(new HomeFragment(), "Home");
            ivBack.setVisibility(View.INVISIBLE);

            ivListGridView.setVisibility(View.INVISIBLE);
            mHome.setImageResource(R.drawable.ic_houseblue);
            tvHome.setTextColor(Color.parseColor("#00c1bd"));
            mCategory.setImageResource(R.drawable.ic_searchwhite);
            tvCategory.setTextColor(Color.parseColor("#004040"));
            mFavourite.setImageResource(R.drawable.ic_likewhite);
            tvFavourite.setTextColor(Color.parseColor("#004040"));
            mCart.setImageResource(R.drawable.ic_cart_white);
            tvCart.setTextColor(Color.parseColor("#004040"));
            mMore.setImageResource(R.drawable.ic_morewhite);
            tvMore.setTextColor(Color.parseColor("#004040"));
        }/*else if (fragment == getSupportFragmentManager().findFragmentByTag("Home")) {
         *//*Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*//*
            finish();
        }*/
    }

    public void setFragment(Fragment fragment, String tag) {
        FragmentTransaction trn = getSupportFragmentManager().beginTransaction();
        trn.addToBackStack(null);
        trn.replace(R.id.nav_host_fragment, fragment, tag);
        trn.commit();
    }
}
