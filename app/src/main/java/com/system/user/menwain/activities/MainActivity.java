package com.system.user.menwain.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.system.user.menwain.custom_languages.BaseActivity;
import com.system.user.menwain.custom_languages.LocaleManager;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.AllListsFragment;
import com.system.user.menwain.fragments.CartFragment;
import com.system.user.menwain.fragments.FavouriteFragment;
import com.system.user.menwain.fragments.CategoryStoresFragment;
import com.system.user.menwain.fragments.HomeFragment;
import com.system.user.menwain.fragments.OrdersFragment;
import com.system.user.menwain.viewmodel.CartViewModel;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public AppBarConfiguration mAppBarConfiguration;
    private ImageView mCart, mFavourite, mHome, mCategory, mMore, ivBack, ivListGridView, ivMenu;
    private LinearLayout more_layout;
    private DrawerLayout drawer;
    Toolbar toolbar;
    private CartViewModel cartViewModel;
    private TextView totalCartQuantity, mActionBarTitle, tvHome, tvCategory, tvCart, tvMore, tvFavourite;
    private String langauge;
    SharedPreferences preferences;
    SharedPreferences.Editor editor, editor1;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        langauge = preferences.getString("my_lang", "");

        if (langauge.isEmpty()) {
            showSelectLangaugeDialog();
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initiateViews();

           /* toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);*/


        more_layout = findViewById(R.id.more_layout);
        more_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(MainActivity.this, R.style.popup_menu);
                PopupMenu popup = new PopupMenu(wrapper, view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_history:
                                mActionBarTitle.setText("History");
                                mHome.setImageResource(R.drawable.ic_housewhite);
                                tvHome.setTextColor(Color.parseColor("#FFFFFF"));
                                mCategory.setImageResource(R.drawable.ic_searchwhite);
                                tvCategory.setTextColor(Color.parseColor("#FFFFFF"));
                                mFavourite.setImageResource(R.drawable.ic_likewhite);
                                tvFavourite.setTextColor(Color.parseColor("#FFFFFF"));
                                mCart.setImageResource(R.drawable.ic_cart_white);
                                tvCart.setTextColor(Color.parseColor("#FFFFFF"));
                                mMore.setImageResource(R.drawable.ic_moreblue);
                                tvMore.setTextColor(Color.parseColor("#00c1bd"));
                                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).commit();
                                return true;
                            case R.id.menu_my_list:
                                mActionBarTitle.setText("List");
                                mHome.setImageResource(R.drawable.ic_housewhite);
                                tvHome.setTextColor(Color.parseColor("#FFFFFF"));
                                mCategory.setImageResource(R.drawable.ic_searchwhite);
                                tvCategory.setTextColor(Color.parseColor("#FFFFFF"));
                                mFavourite.setImageResource(R.drawable.ic_likewhite);
                                tvFavourite.setTextColor(Color.parseColor("#FFFFFF"));
                                mCart.setImageResource(R.drawable.ic_cart_white);
                                tvCart.setTextColor(Color.parseColor("#FFFFFF"));
                                mMore.setImageResource(R.drawable.ic_moreblue);
                                tvMore.setTextColor(Color.parseColor("#00c1bd"));
                                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AllListsFragment()).commit();
                                return true;
                        }
                        return false;
                    }
                });
                popup.inflate(R.menu.popup_menu_more);
                popup.show();
            }
        });

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
        mActionBarTitle.setText("Home");

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });*/

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "Home").commit();
            ivMenu.setVisibility(View.VISIBLE);
        }
    }

    private void initiateViews() {
        ivMenu = findViewById(R.id.iv_open_drawer);
        ivMenu.setOnClickListener(this);

        mHome = findViewById(R.id.home_view);
        tvHome = findViewById(R.id.tv_home_view);

        mCategory = findViewById(R.id.category_view);
        tvCategory = findViewById(R.id.tv_category_view);

        mCart = findViewById(R.id.cart);
        tvCart = findViewById(R.id.tv_cart);

        mFavourite = findViewById(R.id.favourite_view);
        tvFavourite = findViewById(R.id.tv_favourite_view);

        mMore = findViewById(R.id.more);
        tvMore = findViewById(R.id.tv_more);

        totalCartQuantity = findViewById(R.id.total_cart_quantity);
        mActionBarTitle = findViewById(R.id.toolbar_title);

        mHome.setOnClickListener(this);
        mCategory.setOnClickListener(this);
        mFavourite.setOnClickListener(this);
        mCart.setOnClickListener(this);
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_open_drawer) {
            drawer.openDrawer(GravityCompat.START);
        } else if (id == R.id.home_view) {
            mActionBarTitle.setText("Home");
            mHome.setImageResource(R.drawable.ic_houseblue);
            tvHome.setTextColor(Color.parseColor("#00c1bd"));
            mCategory.setImageResource(R.drawable.ic_searchwhite);
            tvCategory.setTextColor(Color.parseColor("#FFFFFF"));
            mFavourite.setImageResource(R.drawable.ic_likewhite);
            tvFavourite.setTextColor(Color.parseColor("#FFFFFF"));
            mCart.setImageResource(R.drawable.ic_cart_white);
            tvCart.setTextColor(Color.parseColor("#FFFFFF"));
            mMore.setImageResource(R.drawable.ic_morewhite);
            tvMore.setTextColor(Color.parseColor("#FFFFFF"));
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "Home").commit();
        } else if (id == R.id.category_view) {
            mActionBarTitle.setText("Category");
            mHome.setImageResource(R.drawable.ic_housewhite);
            tvHome.setTextColor(Color.parseColor("#FFFFFF"));
            mCategory.setImageResource(R.drawable.ic_searchblue);
            tvCategory.setTextColor(Color.parseColor("#00c1bd"));
            mFavourite.setImageResource(R.drawable.ic_likewhite);
            tvFavourite.setTextColor(Color.parseColor("#FFFFFF"));
            mCart.setImageResource(R.drawable.ic_cart_white);
            tvCart.setTextColor(Color.parseColor("#FFFFFF"));
            mMore.setImageResource(R.drawable.ic_morewhite);
            tvMore.setTextColor(Color.parseColor("#FFFFFF"));
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CategoryStoresFragment()).addToBackStack(null).commit();
        } else if (id == R.id.cart) {
            mActionBarTitle.setText("Cart");
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
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).addToBackStack(null).commit();
        } else if (id == R.id.favourite_view) {
            mActionBarTitle.setText("Favourite");
            mHome.setImageResource(R.drawable.ic_housewhite);
            tvHome.setTextColor(Color.parseColor("#FFFFFF"));
            mCategory.setImageResource(R.drawable.ic_searchwhite);
            tvCategory.setTextColor(Color.parseColor("#FFFFFF"));
            mFavourite.setImageResource(R.drawable.ic_likeblue);
            tvFavourite.setTextColor(Color.parseColor("#00c1bd"));
            mCart.setImageResource(R.drawable.ic_cart_white);
            tvCart.setTextColor(Color.parseColor("#FFFFFF"));
            mMore.setImageResource(R.drawable.ic_morewhite);
            tvMore.setTextColor(Color.parseColor("#FFFFFF"));
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new FavouriteFragment()).commit();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_my_lists:
                mActionBarTitle.setText("List");
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AllListsFragment()).commit();
                mHome.setImageResource(R.drawable.ic_housewhite);
                tvHome.setTextColor(Color.parseColor("#FFFFFF"));
                mCategory.setImageResource(R.drawable.ic_searchwhite);
                tvCategory.setTextColor(Color.parseColor("#FFFFFF"));
                mFavourite.setImageResource(R.drawable.ic_likewhite);
                tvFavourite.setTextColor(Color.parseColor("#FFFFFF"));
                mCart.setImageResource(R.drawable.ic_cart_white);
                tvCart.setTextColor(Color.parseColor("#FFFFFF"));
                mMore.setImageResource(R.drawable.ic_moreblue);
                tvMore.setTextColor(Color.parseColor("#00c1bd"));
                break;
            case R.id.nav_my_order:
                mActionBarTitle.setText("History");
                mHome.setImageResource(R.drawable.ic_housewhite);
                tvHome.setTextColor(Color.parseColor("#FFFFFF"));
                mCategory.setImageResource(R.drawable.ic_searchwhite);
                tvCategory.setTextColor(Color.parseColor("#FFFFFF"));
                mFavourite.setImageResource(R.drawable.ic_likewhite);
                tvFavourite.setTextColor(Color.parseColor("#FFFFFF"));
                mCart.setImageResource(R.drawable.ic_cart_white);
                tvCart.setTextColor(Color.parseColor("#FFFFFF"));
                mMore.setImageResource(R.drawable.ic_moreblue);
                tvMore.setTextColor(Color.parseColor("#00c1bd"));
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).commit();
                break;
            case R.id.nav_delivery_addresses:
                startActivity(new Intent(getApplicationContext(), AddressesActivity.class));
                break;
            case R.id.nav_history:
                startActivity(new Intent(getApplicationContext(), OrderHistoryActivity.class));
                break;
            case R.id.nav_favourite:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new FavouriteFragment()).commit();
                mHome.setImageResource(R.drawable.ic_housewhite);
                tvHome.setTextColor(Color.parseColor("#FFFFFF"));
                mCategory.setImageResource(R.drawable.ic_searchwhite);
                tvCategory.setTextColor(Color.parseColor("#FFFFFF"));
                mFavourite.setImageResource(R.drawable.ic_likeblue);
                tvFavourite.setTextColor(Color.parseColor("#00c1bd"));
                mCart.setImageResource(R.drawable.ic_cart_white);
                tvCart.setTextColor(Color.parseColor("#FFFFFF"));
                mMore.setImageResource(R.drawable.ic_morewhite);
                tvMore.setTextColor(Color.parseColor("#FFFFFF"));
            case R.id.nav_rate_app:
                startActivity(new Intent(getApplicationContext(), RateUsActivity.class));
                break;
            case R.id.nav_logout:
                SharedPreferences preferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("phone_no", "");
                editor.apply();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        fragment = getSupportFragmentManager().findFragmentByTag("Home");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } /*else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();

        }else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }*/ else if (fragment != null && fragment.isVisible()) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
        } else {
            setFragment(new HomeFragment(), "Home");
            ivBack = findViewById(R.id.iv_back);
            ivBack.setOnClickListener(this);
            ivBack.setVisibility(View.GONE);

            ivListGridView = findViewById(R.id.iv_grid_list_view);
            ivListGridView.setVisibility(View.GONE);
            ivMenu.setVisibility(View.VISIBLE);

            mActionBarTitle.setText("Home");
            mHome.setImageResource(R.drawable.ic_houseblue);
            tvHome.setTextColor(Color.parseColor("#00c1bd"));
            mCategory.setImageResource(R.drawable.ic_searchwhite);
            tvCategory.setTextColor(Color.parseColor("#FFFFFF"));
            mFavourite.setImageResource(R.drawable.ic_likewhite);
            tvFavourite.setTextColor(Color.parseColor("#FFFFFF"));
            mCart.setImageResource(R.drawable.ic_cart_white);
            tvCart.setTextColor(Color.parseColor("#FFFFFF"));
            mMore.setImageResource(R.drawable.ic_morewhite);
            tvMore.setTextColor(Color.parseColor("#FFFFFF"));
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
