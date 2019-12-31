package com.system.user.menwain.fragments.others;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.Item_details_SlidingImages_Adapter;
import com.system.user.menwain.adapters.category_adapters.SelectedItemsFilterAdapter;
import com.system.user.menwain.fragments.category.ItemsFragment;
import com.system.user.menwain.fragments.home.HomeFragment;

public class ItemDetailsFragment extends Fragment implements View.OnClickListener {
    private int[] IMAGES; /*= {R.drawable.dis, R.drawable.disc, R.drawable.disco, R.drawable.discoun, R.drawable.discount};*/
    private String[] storesName = {"Madina c carry", "Metro c carry", "Makro c carry", "Pak c carry", "Alrasheed c carry", "ARY c carry",
            "Meezan c carry", "Lahore c carry", "ARY c carry", "Meezan c carry"};
    private String[] productsName = {"Cooking oil", "Chicken", "Meat", "Butter", "Eggs", "Chocolate", "Frouts", "Carrot", "Mango", "Vegetables"};
    private int[] items = {R.drawable.coocking_oil, R.drawable.chikken, R.drawable.meat, R.drawable.butter, R.drawable.eggs, R.drawable.choclate, R.drawable.frouts,
            R.drawable.carrot, R.drawable.mango, R.drawable.vagitables};
    RecyclerView recyclerViewRelateItems;
    SelectedItemsFilterAdapter selectedItemsFilterAdapter;
    TextView mDescription, mSpecification, mReviews;
    TextView mTextView, mAddToCart;
    ImageView mCart, mBack, mItem;
    Bundle bundle;
    String status;
    private static ViewPager mPager;
    private TabLayout tabLayout;
    private static int NUM_PAGES = 0;
    final long PERIOD_MS = 3000;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private CardView mSearchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        getFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemDescriptionFragment()).commit();
        mSearchView = getActivity().findViewById(R.id.search_view);
        mSearchView.setVisibility(View.INVISIBLE);
        mPager = view.findViewById(R.id.item_detail_pager);
        tabLayout = view.findViewById(R.id.tab_layout_item_details);


        // mItem = view.findViewById(R.id.selected_item_view);
        bundle = this.getArguments();
        IMAGES = new int[]{(Integer.parseInt(bundle.getString("image_url", ""))), (Integer.parseInt(bundle.getString("image_url1", ""))), (Integer.parseInt(bundle.getString("image_url2", "")))};
        status = bundle.getString("status", "");

        mDescription = view.findViewById(R.id.description_btn);
        mSpecification = view.findViewById(R.id.specificatin_btn);
        mReviews = view.findViewById(R.id.reviews_btn);
        mTextView = view.findViewById(R.id.text_view);
        mAddToCart = view.findViewById(R.id.add_to_cart);
        mCart = view.findViewById(R.id.cart);
        mBack = getActivity().findViewById(R.id.iv_back);
        mBack.setVisibility(View.VISIBLE);

        mDescription.setOnClickListener(this);
        mSpecification.setOnClickListener(this);
        mReviews.setOnClickListener(this);
        mAddToCart.setOnClickListener(this);
        mBack.setOnClickListener(this);


        recyclerViewRelateItems = view.findViewById(R.id.recycler_view_related_items_item_details);
        recyclerViewRelateItems.setHasFixedSize(true);
        recyclerViewRelateItems.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        selectedItemsFilterAdapter = new SelectedItemsFilterAdapter(getContext(), productsName, items, storesName);
        recyclerViewRelateItems.setAdapter(selectedItemsFilterAdapter);
        init();
        return view;
    }

    private void init() {
        mPager.setAdapter(new Item_details_SlidingImages_Adapter(getContext(), IMAGES));
        tabLayout.setupWithViewPager(mPager, true);
        NUM_PAGES = IMAGES.length;
        /*After setting the adapter use the timer */
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (IMAGES.length == currentPage) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                mPager.setCurrentItem(currentPage, true);
                handler.postDelayed(this, PERIOD_MS);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, PERIOD_MS);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.description_btn) {
            getFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemDescriptionFragment()).commit();
            mDescription.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item_details_btn_selected));
            mSpecification.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item_details_btn_unselected));
            mReviews.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item_details_btn_unselected));
        } else if (id == R.id.specificatin_btn) {
            getFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemSpecificationFragment()).commit();
            mDescription.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item_details_btn_unselected));
            mSpecification.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item_details_btn_selected));
            mReviews.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item_details_btn_unselected));
        } else if (id == R.id.reviews_btn) {
            getFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemReviewsFragment()).commit();
            mDescription.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item_details_btn_unselected));
            mSpecification.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item_details_btn_unselected));
            mReviews.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item_details_btn_selected));
        } else if (id == R.id.add_to_cart) {

        } else if (id == R.id.iv_back) {
            if (status == "1") {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).addToBackStack(null).commit();
            } else {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ItemsFragment()).addToBackStack(null).commit();
            }
        }
    }

}
