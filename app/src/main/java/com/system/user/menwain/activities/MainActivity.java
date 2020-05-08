package com.system.user.menwain.activities;

import android.app.Activity;
import android.app.Dialog;
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

import com.system.user.menwain.fragments.cart.AddCardFragment;
import com.system.user.menwain.fragments.category.SuperCategoryFragment;
import com.system.user.menwain.fragments.others.SearchFragment;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.custom_languages.BaseActivity;
import com.system.user.menwain.custom_languages.LocaleManager;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.cart.AvailNotAvailItemsListsFragment;
import com.system.user.menwain.fragments.cart.ItemsAvailabilityStoresFragment;
import com.system.user.menwain.fragments.category.CategoryItemsFragment;
import com.system.user.menwain.fragments.more.orders.OrderDetailsFragment;
import com.system.user.menwain.fragments.more.orders.OrdersFragment;
import com.system.user.menwain.fragments.more.stores.SelectedStoreFragment;
import com.system.user.menwain.fragments.more.stores.StoresFragment;
import com.system.user.menwain.fragments.my_list.AllListsFragment;
import com.system.user.menwain.fragments.cart.CartFragment;
import com.system.user.menwain.fragments.cart.DeliveryAddressFragment;
import com.system.user.menwain.fragments.home.HomeFragment;
import com.system.user.menwain.fragments.more.MoreFragment;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivSearch, mBarCodeScanner, mCart, mFavourite, mHome, mCategory, mMore, ivBack, ivListGridView, ivMenu;
    private LinearLayout home_layout, category_layout, my_list_layout, more_layout;
    private RelativeLayout cart_layout;
    private CartViewModel cartViewModel;
    private TextView totalCartQuantity, tvHome, tvCategory, tvCart, tvMore, tvFavourite;
    private EditText etSearch;
    boolean isLogin, isSignUp = false;
    private int frag_status;
    Preferences prefrences;
    private Dialog MyDialog;
    private Button btnYes, btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        prefrences = new Preferences(this);
        frag_status = prefrences.getCartFragStatus();
        Intent intent = getIntent();
        Log.e("IS LOGIN", String.valueOf(isLogin));
        if (intent != null) {
            Log.e("IS LOGIN", String.valueOf(isLogin));
            isLogin = intent.getBooleanExtra("isLogin", false);
            isSignUp = intent.getBooleanExtra("is_sign_up", false);
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


        if (savedInstanceState == null) {
            if (isLogin == true) {
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
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DeliveryAddressFragment(), "del").commit();
            } else if (isSignUp == true) {
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
        ivSearch = findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(this);
        etSearch = findViewById(R.id.et_search);

        mBarCodeScanner = findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(this);

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

    @Override
    protected void onResume() {
        super.onResume();
        String barCode = ScanActivity.barCode;
        if (barCode != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SearchFragment()).commit();
        } else {

        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_search) {
            Bundle bundle = new Bundle();
            SearchFragment fragment = new SearchFragment();
            if (etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim() == null) {
                Toast.makeText(getApplicationContext(), "Enter Your desire search..", Toast.LENGTH_SHORT).show();
            } else {
                bundle.putString("search", etSearch.getText().toString().trim());
                etSearch.setText("");
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
            }
        } else if (id == R.id.bar_code_code_scanner_home) {
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            startActivity(intent);
        } else if (id == R.id.home_layout) {
            //  mActionBarTitle.setText("Home");
            prefrences.setBottomNavStatus(1);
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
            prefrences.setBottomNavStatus(2);
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
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SuperCategoryFragment()).addToBackStack(null).commit();
        } else if (id == R.id.cart_layout) {
            prefrences.setBottomNavStatus(3);
            frag_status = prefrences.getCartFragStatus();
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
            } else if (frag_status == 3) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AvailNotAvailItemsListsFragment()).addToBackStack(null).commit();
            } else if (frag_status == 4) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AddCardFragment()).addToBackStack(null).commit();
            }
        } else if (id == R.id.my_list_layout) {
            prefrences.setBottomNavStatus(4);
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
            prefrences.setBottomNavStatus(5);
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
        int cart_fragment_status = prefrences.getCartFragStatus();
        int home_fragment_status = prefrences.getHomeFragStatus();
        int category_fragment_status = prefrences.getCategoryFragStatus();
        int my_list_fragment_status = prefrences.getMyListFragStatus();
        int more_status = prefrences.getMoreFragStatus();
        int stores_fragment_status = prefrences.getMoreStoresFragStatus();
        int orders_fragment_status = prefrences.getMoreOrdersFragStatus();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("Home");
        if (fragment != null && fragment.isVisible()) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MyCustomAlertDialog();
            //exitAppDialog();
            //  finish();
        } else if (prefrences.getBottomNavStatus() == 1) {
            if (home_fragment_status == 4) {
                prefrences.setHomeFragStatus(3);
                setFragment(new HomeFragment(), "Home");
            } else if (home_fragment_status == 3) {
                prefrences.setHomeFragStatus(2);
                setFragment(new HomeFragment(), "Home");
            } else if (home_fragment_status == 2) {
                prefrences.setHomeFragStatus(1);
                setFragment(new HomeFragment(), "Home");
            } else if (home_fragment_status == 1) {
                prefrences.setBottomNavStatus(1);
                prefrences.setHomeFragStatus(0);
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
            }
        } else if (prefrences.getBottomNavStatus() == 2) {
            if (category_fragment_status == 2) {
                prefrences.setCategoryFragStatus(1);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CategoryItemsFragment()).addToBackStack(null).commit();
            } else if (category_fragment_status == 1) {
                prefrences.setCategoryFragStatus(0);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SuperCategoryFragment()).addToBackStack(null).commit();
            } else if (category_fragment_status == 0) {
                prefrences.setBottomNavStatus(1);
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
            }
        } else if (prefrences.getBottomNavStatus() == 3) {
            if (cart_fragment_status == 4) {
                prefrences.setCartFragStatus(3);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AvailNotAvailItemsListsFragment()).addToBackStack(null).commit();
            } else if (cart_fragment_status == 3) {
                prefrences.setCartFragStatus(2);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ItemsAvailabilityStoresFragment()).addToBackStack(null).commit();
            } else if (cart_fragment_status == 2) {
                prefrences.setCartFragStatus(1);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DeliveryAddressFragment()).addToBackStack(null).commit();
            } else if (cart_fragment_status == 1) {
                prefrences.setCartFragStatus(0);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).addToBackStack(null).commit();
            } else {
                prefrences.setBottomNavStatus(1);
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
            }
        } else if (prefrences.getBottomNavStatus() == 4) {
            if (my_list_fragment_status == 1) {
                prefrences.setMyListFragStatus(0);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AllListsFragment()).addToBackStack(null).commit();
            } else {
                prefrences.setBottomNavStatus(1);
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
            }
        } else if (prefrences.getBottomNavStatus() == 5) {
            if (more_status == 1) {
                if (stores_fragment_status == 3) {
                    prefrences.setMoreStoresFragStatus(2);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SelectedStoreFragment()).addToBackStack(null).commit();
                } else if (stores_fragment_status == 2) {
                    prefrences.setMoreStoresFragStatus(1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new StoresFragment()).addToBackStack(null).commit();
                } else if (stores_fragment_status == 1) {
                    prefrences.setMoreStoresFragStatus(0);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new MoreFragment()).addToBackStack(null).commit();
                } else {
                    prefrences.setBottomNavStatus(1);
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
                }
            } else if (more_status == 2) {
                if (orders_fragment_status == 3) {
                    prefrences.setMoreOrdersFragStatus(2);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrderDetailsFragment()).addToBackStack(null).commit();
                } else if (orders_fragment_status == 2) {
                    prefrences.setMoreOrdersFragStatus(1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).addToBackStack(null).commit();
                } else if (orders_fragment_status == 1) {
                    prefrences.setMoreOrdersFragStatus(0);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new MoreFragment()).addToBackStack(null).commit();
                } else {
                    prefrences.setBottomNavStatus(1);
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
                }
            } else if (more_status == 3) {
                if (prefrences.getProfileFragStatus() == 1) {
                    prefrences.setProfileFragStatus(0);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new MoreFragment()).addToBackStack(null).commit();
                } else {
                    prefrences.setBottomNavStatus(1);
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
                }
            }
        } else if (prefrences.getBottomNavStatus() == 6) {
            prefrences.setBottomNavStatus(3);
            if (cart_fragment_status == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).addToBackStack(null).commit();
            } else if (cart_fragment_status == 1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DeliveryAddressFragment()).addToBackStack(null).commit();
            } else if (cart_fragment_status == 2) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ItemsAvailabilityStoresFragment()).addToBackStack(null).commit();
            } else if (cart_fragment_status == 3) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AvailNotAvailItemsListsFragment()).addToBackStack(null).commit();
            }
        }
    }

    public void MyCustomAlertDialog() {
        MyDialog = new Dialog(MainActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialog);
        btnYes = MyDialog.findViewById(R.id.btn_yes);
        btnNo = MyDialog.findViewById(R.id.btn_no);
        btnYes.setEnabled(true);
        btnNo.setEnabled(true);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.cancel();
            }
        });
        MyDialog.show();
    }

    private void exitAppDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void setFragment(Fragment fragment, String tag) {
        FragmentTransaction trn = getSupportFragmentManager().beginTransaction();
        trn.addToBackStack(null);
        trn.replace(R.id.nav_host_fragment, fragment, tag);
        trn.commit();
    }
}
