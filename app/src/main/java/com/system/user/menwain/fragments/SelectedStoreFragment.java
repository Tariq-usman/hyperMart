package com.system.user.menwain.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.FilterItemsAdapter;
import com.system.user.menwain.adapters.SelectedItemAdapter;
import com.system.user.menwain.adapters.SelectedItemsNamesAdapter;
import com.system.user.menwain.adapters.SelectedStoreAdapter;
import com.system.user.menwain.adapters.StoresAdapter;
import com.system.user.menwain.custom_layout_manager.CenterZoomLayoutManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectedStoreFragment extends Fragment{

    private RecyclerView recyclerViewProductCategory,recyclerViewItemNames,recyclerViewFilterItems;
    private SelectedItemAdapter selectedItemAdapter;
    private FilterItemsAdapter filterItemsAdapter;
    private LinearLayoutManager centerZoomLayoutManager;
    private Context context;


    private String[] storesName = {"Madina", "Metro", "Makro", "Pak", "Alrasheed", "ARY", "Meezan", "Lahore", "ARY", "Meezan", "Lahore"};
    private int[] stores = {R.drawable.madina, R.drawable.metro, R.drawable.makro, R.drawable.pak, R.drawable.alrasheed_cash_carry, R.drawable.ary_cash_carry, R.drawable.meezan, R.drawable.lahore,R.drawable.ary_cash_carry, R.drawable.meezan, R.drawable.lahore};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_store,container,false);

        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_selected_store);
        recyclerViewProductCategory.setHasFixedSize(true);
        centerZoomLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProductCategory.setLayoutManager( centerZoomLayoutManager);
        recyclerViewProductCategory.setAdapter(new SelectedStoreAdapter(storesName, getContext(),stores));

        int position = StoresAdapter.pos;
        recyclerViewProductCategory.scrollToPosition(position);



       /* recyclerViewItemNames = view.findViewById(R.id.recycler_view_selected_items_name);
        recyclerViewItemNames.setHasFixedSize(true);
        recyclerViewItemNames.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        recyclerViewItemNames.setAdapter(new SelectedItemsNamesAdapter(getContext(),productsName));*/




       /* recyclerViewFilterItems = view.findViewById(R.id.recycler_view_filter_items);
        recyclerViewFilterItems.setHasFixedSize(true);
        recyclerViewFilterItems.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
        recyclerViewFilterItems.setAdapter( new FilterItemsAdapter(productsName, getContext(),items,storesName));
*/
        return view;
    }

}
