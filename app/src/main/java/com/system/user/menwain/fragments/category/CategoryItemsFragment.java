package com.system.user.menwain.fragments.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.category_adapters.CategoryAdapter;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.category_adapters.FilterItemsAdapter;
import com.system.user.menwain.adapters.category_adapters.SelectedItemAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryItemsFragment extends Fragment {

    private RecyclerView recyclerViewProductCategory, recyclerViewItemNames, recyclerViewFilterItems;
    private LinearLayoutManager linearLayoutManager;
    private int getPreviousId = CategoryAdapter.passId;
    private ImageView mBackBtn,mBarCodeScanner;

    private CardView mSearchViewItemsFragment;
    private Prefrences prefrences;

    private String[] storesName = {"Madina c carry", "Metro c carry", "Makro c carry", "Pak c carry", "Alrasheed c carry", "ARY c carry",
            "Meezan c carry", "Lahore c carry", "ARY c carry", "Meezan c carry"};
    private String[] productsName = {"Cooking oil", "Chicken", "Meat", "Butter", "Eggs", "Chocolate", "Frouts", "Carrot", "Mango", "Vegetables"};
    private int[] items = {R.drawable.coocking_oil, R.drawable.chikken, R.drawable.meat, R.drawable.butter, R.drawable.eggs, R.drawable.choclate, R.drawable.frouts,
            R.drawable.carrot, R.drawable.mango, R.drawable.vagitables};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_items, container, false);
        prefrences = new Prefrences(getContext());
        mSearchViewItemsFragment = getActivity().findViewById(R.id.search_view);
        //mSearchViewItemsFragment.setVisibility(View.INVISIBLE);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setCategoryFragStatus(0);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CategoryFragment()).addToBackStack(null).commit();
                mBackBtn.setVisibility(View.GONE);
            }
        });

        mBarCodeScanner = getActivity().findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_selected_items);
        recyclerViewProductCategory.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProductCategory.setLayoutManager(linearLayoutManager);
        recyclerViewProductCategory.setAdapter(new SelectedItemAdapter(getContext(), productsName, items));
        recyclerViewProductCategory.smoothScrollToPosition(getPreviousId + 1);

        recyclerViewFilterItems = view.findViewById(R.id.recycler_view_filter_items);
        recyclerViewFilterItems.setHasFixedSize(true);
        recyclerViewFilterItems.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        recyclerViewFilterItems.setAdapter(new FilterItemsAdapter(productsName, getContext(), items, storesName));

        return view;
    }

}
