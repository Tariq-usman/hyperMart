package com.system.user.menwain.fragments.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.AvailableItemsListAdapter;
import com.system.user.menwain.adapters.cart_adapters.AvailableItemsListRadiusAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresRadiusAdapter;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.responses.cart.AvailNotAvailRadiusResponse;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AvailableItemsFragment extends Fragment implements RecyclerClickInterface {
    RecyclerView recyclerViewItemsList;
    private AvailableItemsListAdapter availableItemsListAdapter;
    private AvailableItemsListRadiusAdapter availableItemsListRadiusAdapter;
    public static int avai_items;
    SharedPreferences.Editor editor;
    List<AvailNotAvailResponse.Datum.Available> avail_items_list= ItemsAvailabilityStoresAdapter.available_list;
    List<AvailNotAvailRadiusResponse.Datum.Available> avail_items_list_radius= ItemsAvailabilityStoresRadiusAdapter.available_list;

    List<AvailNotAvailResponse.Datum.Notavailable> not_avail_items_list = ItemsAvailabilityStoresAdapter.not_available_list;
    List<AvailNotAvailRadiusResponse.Datum.Notavailable> not_avail_items_list_radius = ItemsAvailabilityStoresRadiusAdapter.not_available_list;

    private TextView mTotalAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_available_items, container, false);


        recyclerViewItemsList = view.findViewById(R.id.recycler_view_available_items_list);
        recyclerViewItemsList.setHasFixedSize(true);
        recyclerViewItemsList.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            if (avail_items_list.size() > 0) {
                AvailNotAvailItemsListsFragment.mAvailItems.setText(avail_items_list.size()+"");
                AvailNotAvailItemsListsFragment.mNotAvailItmes.setText(not_avail_items_list.size()+"");
                availableItemsListAdapter = new AvailableItemsListAdapter(getContext(), avail_items_list, this);
                recyclerViewItemsList.setAdapter(availableItemsListAdapter);
            } else if (avail_items_list_radius.size() > 0) {
                AvailNotAvailItemsListsFragment.mAvailItems.setText(avail_items_list_radius.size()+"");
                AvailNotAvailItemsListsFragment.mNotAvailItmes.setText(not_avail_items_list_radius.size()+"");
                availableItemsListRadiusAdapter = new AvailableItemsListRadiusAdapter(getContext(), avail_items_list_radius, this);
                recyclerViewItemsList.setAdapter(availableItemsListRadiusAdapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void interfaceOnClick(View view, int position) {

    }

}
