package com.system.user.menwain;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.system.user.menwain.fragments.AllListsFragment;
import com.system.user.menwain.fragments.CartFragment;
import com.system.user.menwain.fragments.FavouriteFragment;
import com.system.user.menwain.fragments.HomeFragment;
import com.system.user.menwain.fragments.OrdersFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public AppBarConfiguration mAppBarConfiguration;
    ImageView mCart, mListing, mHome, mFavourite, mHistory;
    DrawerLayout drawer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        toolbar = findViewById(R.id.toolbar);
//         getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSupportActionBar(toolbar);

        mHome = findViewById(R.id.home_view);
        mCart = findViewById(R.id.cart);
        mListing = findViewById(R.id.listing_view);
        mFavourite = findViewById(R.id.favourite);
        mHistory = findViewById(R.id.history);

        mHome.setOnClickListener(this);
        mListing.setOnClickListener(this);
        mCart.setOnClickListener(this);
        mFavourite.setOnClickListener(this);
        mHistory.setOnClickListener(this);


        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        mHome.setImageResource(R.drawable.ic_homeselected);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "Home").commit();
            //navigationView.setCheckedItem(R.id.nav_beginning);
        }
       /* NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
        if (id == R.id.home_view) {
            mHome.setImageResource(R.drawable.ic_homeselected);
            mListing.setImageResource(R.drawable.ic_listing);
            mCart.setImageResource(R.drawable.ic_cart);
            mHistory.setImageResource(R.drawable.ic_history);
            mFavourite.setImageResource(R.drawable.ic_wishlist);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "Home").commit();
        } else if (id == R.id.cart) {
            mCart.setImageResource(R.drawable.ic_cartblue);
            mListing.setImageResource(R.drawable.ic_listing);
            mHistory.setImageResource(R.drawable.ic_history);
            mHome.setImageResource(R.drawable.ic_homewwhite);
            mFavourite.setImageResource(R.drawable.ic_wishlist);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).commit();
        } else if (id == R.id.history) {
            mHistory.setImageResource(R.drawable.ic_historyblue);
            mListing.setImageResource(R.drawable.ic_listing);
            mCart.setImageResource(R.drawable.ic_cart);
            mHome.setImageResource(R.drawable.ic_homewwhite);
            mFavourite.setImageResource(R.drawable.ic_wishlist);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).commit();
        } else if (id == R.id.listing_view) {
            mListing.setImageResource(R.drawable.ic_listingblue);
            mCart.setImageResource(R.drawable.ic_cart);
            mHistory.setImageResource(R.drawable.ic_history);
            mHome.setImageResource(R.drawable.ic_homewwhite);
            mFavourite.setImageResource(R.drawable.ic_wishlist);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AllListsFragment()).commit();

        } else if (id == R.id.favourite) {
            mFavourite.setImageResource(R.drawable.ic_wishlistblue);
            mListing.setImageResource(R.drawable.ic_listing);
            mCart.setImageResource(R.drawable.ic_cart);
            mHome.setImageResource(R.drawable.ic_homewwhite);
            mHistory.setImageResource(R.drawable.ic_history);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new FavouriteFragment()).commit();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_my_lists:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AllListsFragment()).commit();
                mListing.setImageResource(R.drawable.ic_listingblue);
                mHome.setImageResource(R.drawable.ic_homewwhite);
                break;
            case R.id.nav_my_order:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).commit();
                mHistory.setImageResource(R.drawable.ic_historyblue);
                break;
            case R.id.nav_delivery_addresses:
                startActivity(new Intent(getApplicationContext(), AddressesActivity.class));
                break;
            case R.id.nav_history:
                startActivity(new Intent(getApplicationContext(), OrderHistoryActivity.class));
                break;
            case R.id.nav_favourite:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new FavouriteFragment()).commit();
                break;
            case R.id.nav_rate_app:
                startActivity(new Intent(getApplicationContext(), RateUsActivity.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("Home");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragment != null && fragment.isVisible()) {
            finish();
        } else {
            setFragment(new HomeFragment(), "Home");
            mHome.setImageResource(R.drawable.ic_homeselected);
            mCart.setImageResource(R.drawable.ic_cart);
            mHistory.setImageResource(R.drawable.ic_history);
            mListing.setImageResource(R.drawable.ic_listing);
            mFavourite.setImageResource(R.drawable.ic_wishlist);
        }
    }

    public void setFragment(Fragment fragment, String tag) {
        FragmentTransaction trn = getSupportFragmentManager().beginTransaction();
        trn.addToBackStack(null);
        trn.replace(R.id.nav_host_fragment, fragment, tag);
        trn.commit();
    }
}
