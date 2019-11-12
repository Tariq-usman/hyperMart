package com.system.user.menwain;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.system.user.menwain.fragments.AllListsFragment;
import com.system.user.menwain.fragments.CartFragment;
import com.system.user.menwain.fragments.HomeFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public AppBarConfiguration mAppBarConfiguration;
    ImageView mCart,mListing,mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHome = findViewById(R.id.home_view);
        mCart = findViewById(R.id.cart);
        mListing = findViewById(R.id.listing_view);

        mHome.setOnClickListener(this);
        mListing.setOnClickListener(this);
        mCart.setOnClickListener(this);

       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
       DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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

        if (id==R.id.cart){
            mCart.setImageResource(R.drawable.ic_cartblue);
            mListing.setImageResource(R.drawable.ic_listing);
            mHome.setImageResource(R.drawable.ic_homewwhite);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new CartFragment()).commit();
        }else if (id == R.id.listing_view){
            mListing.setImageResource(R.drawable.ic_listingblue);
            mCart.setImageResource(R.drawable.ic_cart);
            mHome.setImageResource(R.drawable.ic_homewwhite);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new AllListsFragment()).commit();

        }else if (id == R.id.home_view){
            mListing.setImageResource(R.drawable.ic_listing);
            mCart.setImageResource(R.drawable.ic_cart);
            mHome.setImageResource(R.drawable.ic_homeselected);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();

        }
    }
}
