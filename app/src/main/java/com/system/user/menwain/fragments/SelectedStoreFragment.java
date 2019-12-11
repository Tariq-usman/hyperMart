package com.system.user.menwain.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.FilterItemsAdapter;
import com.system.user.menwain.adapters.FilteredStoresAdapter;
import com.system.user.menwain.adapters.SelectedItemAdapter;
import com.system.user.menwain.adapters.SelectedStoreAdapter;
import com.system.user.menwain.adapters.SelectedStoresNamesAdapter;
import com.system.user.menwain.adapters.StoresAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectedStoreFragment extends Fragment {

    private RecyclerView recyclerViewSelectedStore, recyclerViewStoresName, recyclerViewFilterStores;
    private SelectedItemAdapter selectedItemAdapter;
    private FilterItemsAdapter filterItemsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Context context;


    private String[] storesName = {"Madina", "Metro", "Makro", "Pak", "Alrasheed", "ARY", "Meezan", "Lahore", "ARY", "Meezan", "Lahore"};
    private int[] stores = {R.drawable.madina, R.drawable.metro, R.drawable.makro, R.drawable.pak, R.drawable.alrasheed_cash_carry, R.drawable.ary_cash_carry, R.drawable.meezan, R.drawable.lahore, R.drawable.ary_cash_carry, R.drawable.meezan, R.drawable.lahore};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_store, container, false);

        recyclerViewSelectedStore = view.findViewById(R.id.recycler_view_selected_store);
        recyclerViewSelectedStore.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSelectedStore.setLayoutManager(linearLayoutManager);
        recyclerViewSelectedStore.setAdapter(new SelectedStoreAdapter(storesName, getContext(), stores));
        int position = StoresAdapter.pos;
        recyclerViewSelectedStore.smoothScrollToPosition(position + 1);


        recyclerViewStoresName = view.findViewById(R.id.recycler_view_selected_stores_name);
        recyclerViewStoresName.setHasFixedSize(true);
        recyclerViewStoresName.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewStoresName.setAdapter(new SelectedStoresNamesAdapter(getContext(), storesName));


        recyclerViewFilterStores = view.findViewById(R.id.recycler_view_filter_filtered);
        recyclerViewFilterStores.setHasFixedSize(true);
        recyclerViewFilterStores.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        recyclerViewFilterStores.setAdapter(new FilteredStoresAdapter(storesName, getContext(), stores));

        return view;
    }

}