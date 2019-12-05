package com.system.user.menwain.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.adapters.CategoryAdapter;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.FilterItemsAdapter;
import com.system.user.menwain.adapters.SelectedItemAdapter;
import com.system.user.menwain.adapters.SelectedItemsNamesAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ItemsFragment extends Fragment {

    private RecyclerView recyclerViewProductCategory, recyclerViewItemNames, recyclerViewFilterItems;
    private SelectedItemAdapter selectedItemAdapter;
    private FilterItemsAdapter filterItemsAdapter;
    private Context context;
    private LinearLayoutManager linearLayoutManager;
    private int getPreviousId = CategoryAdapter.passId;

    // private String [] productsName={"Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese"};
    private String[] storesName = {"Madina c carry", "Metro c carry", "Makro c carry", "Pak c carry", "Alrasheed c carry", "ARY c carry",
            "Meezan c carry", "Lahore c carry", "ARY c carry", "Meezan c carry"};
    private String[] productsName = {"Cooking oil", "Chicken", "Meat", "Butter", "Eggs", "Chocolate", "Frouts", "Carrot", "Mango", "Vegetables"};
    private int[] items = {R.drawable.coocking_oil, R.drawable.chikken, R.drawable.meat, R.drawable.butter, R.drawable.eggs, R.drawable.choclate, R.drawable.frouts,
            R.drawable.carrot, R.drawable.mango, R.drawable.vagitables};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_items, container, false);


        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_selected_items);
        recyclerViewProductCategory.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProductCategory.setLayoutManager(linearLayoutManager);
        recyclerViewProductCategory.setAdapter(new SelectedItemAdapter(getContext(), productsName, items));
        recyclerViewProductCategory.smoothScrollToPosition(getPreviousId + 1);


        recyclerViewItemNames = view.findViewById(R.id.recycler_view_selected_items_name);
        recyclerViewItemNames.setHasFixedSize(true);
        recyclerViewItemNames.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewItemNames.setAdapter(new SelectedItemsNamesAdapter(getContext(), productsName));


        recyclerViewFilterItems = view.findViewById(R.id.recycler_view_filter_items);
        recyclerViewFilterItems.setHasFixedSize(true);
        recyclerViewFilterItems.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerViewFilterItems.setAdapter(new FilterItemsAdapter(productsName, getContext(), items, storesName));

        return view;
    }

}
