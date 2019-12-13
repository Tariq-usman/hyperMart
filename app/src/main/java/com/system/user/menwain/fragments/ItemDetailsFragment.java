package com.system.user.menwain.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.SelectedItemsFilterAdapter;

public class ItemDetailsFragment extends Fragment implements View.OnClickListener {

    private String [] storesName={"Madina c carry","Metro c carry","Makro c carry","Pak c carry","Alrasheed c carry","ARY c carry",
            "Meezan c carry","Lahore c carry","ARY c carry","Meezan c carry"};
    private String [] productsName={"Cooking oil","Chicken","Meat","Butter","Eggs","Chocolate","Frouts","Carrot","Mango","Vegetables"};
    private int [] items = {R.drawable.coocking_oil,R.drawable.chikken,R.drawable.meat,R.drawable.butter,R.drawable.eggs,R.drawable.choclate,R.drawable.frouts,
            R.drawable.carrot,R.drawable.mango,R.drawable.vagitables};
    RecyclerView recyclerViewRelateItems;
    SelectedItemsFilterAdapter selectedItemsFilterAdapter;
    TextView mDescription, mSpecification, mReviews;
    TextView mTextView, mAddToCart,mTitleItemDetails;
    ImageView mCart,mBack,mMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_item_details,container,false);

        getFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemDescriptionFragment()).commit();

        mDescription = view.findViewById(R.id.description_btn);
        mSpecification = view.findViewById(R.id.specificatin_btn);
        mReviews = view.findViewById(R.id.reviews_btn);
        mTextView = view.findViewById(R.id.text_view);
        mAddToCart = view.findViewById(R.id.add_to_cart);
        mCart = view.findViewById(R.id.cart);
        mBack = getActivity().findViewById(R.id.iv_back);
        mBack.setVisibility(View.VISIBLE);
        mMenu = getActivity().findViewById(R.id.iv_open_drawer);
        mMenu.setVisibility(View.GONE);

        mTitleItemDetails = getActivity().findViewById(R.id.toolbar_title);
        mTitleItemDetails.setText("Item Details");

        mDescription.setOnClickListener(this);
        mSpecification.setOnClickListener(this);
        mReviews.setOnClickListener(this);
        mAddToCart.setOnClickListener(this);
        mBack.setOnClickListener(this);


        recyclerViewRelateItems = view.findViewById(R.id.recycler_view_related_items_item_details);
        recyclerViewRelateItems.setHasFixedSize(true);
        recyclerViewRelateItems.setLayoutManager(new GridLayoutManager(getContext(),
                2, GridLayoutManager.VERTICAL, false));
        selectedItemsFilterAdapter = new SelectedItemsFilterAdapter(getContext(),productsName,items,storesName);
        recyclerViewRelateItems.setAdapter(selectedItemsFilterAdapter);
    return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.description_btn) {
            getFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemDescriptionFragment()).commit();
            mDescription.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_selected));
            mSpecification.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            mReviews.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
        } else if (id == R.id.specificatin_btn) {
            getFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemSpecificationFragment()).commit();
            mDescription.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            mSpecification.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_selected));
            mReviews.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
        } else if (id == R.id.reviews_btn) {
            getFragmentManager().beginTransaction().replace(R.id.d_s_r_container, new ItemReviewsFragment()).commit();
            mDescription.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            mSpecification.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            mReviews.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_selected));
        }else if (id == R.id.add_to_cart){

        }else if (id == R.id.iv_back){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ItemsFragment()).addToBackStack(null).commit();
            mTitleItemDetails.setText("Items");
        }
    }

}
