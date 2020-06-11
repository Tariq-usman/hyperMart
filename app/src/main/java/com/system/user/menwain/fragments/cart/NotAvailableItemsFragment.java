package com.system.user.menwain.fragments.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilitySelectedStoresAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresRadiusAdapter;
import com.system.user.menwain.adapters.cart_adapters.NotAvailableItemsListAdapter;
import com.system.user.menwain.adapters.cart_adapters.NotAvailableItemsListRadiusAdapter;
import com.system.user.menwain.adapters.cart_adapters.NotAvailableItemsSelectedStoreListAdapter;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.cart.AvailNotAvailRadiusResponse;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;
import com.system.user.menwain.responses.cart.SelectedStoreProductsResponse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotAvailableItemsFragment extends Fragment {
    private String[] productsName = {"Produce", "Meat & Poultry", "Milk & Cheese"};

    RecyclerView recyclerViewItemsList;
    NotAvailableItemsListAdapter notAvailableItemsListAdapter;
    NotAvailableItemsListRadiusAdapter notAvailableItemsListRadiusAdapter;
    NotAvailableItemsSelectedStoreListAdapter notAvailableItemsSelectedStoreListAdapter;
    SharedPreferences.Editor editor;
    public static int not_avail_items;
    List<AvailNotAvailResponse.Datum.Notavailable> not_avail_items_list = ItemsAvailabilityStoresAdapter.not_available_list;
    List<AvailNotAvailRadiusResponse.Datum.Notavailable> not_avail_items_list_radius = ItemsAvailabilityStoresRadiusAdapter.not_available_list;
    List<SelectedStoreProductsResponse.Datum.Notavailable> not_avail_items_selected_store_and_radius = ItemsAvailabilitySelectedStoresAdapter.not_available_list;
    private Preferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_not_available_items, container, false);
        preferences = new Preferences(getContext());
        recyclerViewItemsList = view.findViewById(R.id.recycler_view_not_available_items_list);
        recyclerViewItemsList.setHasFixedSize(true);
        recyclerViewItemsList.setLayoutManager(new LinearLayoutManager(getContext()));
        if (preferences.getOrderStatus() == 0) {
            if (not_avail_items_list.size() > 0) {
                notAvailableItemsListAdapter = new NotAvailableItemsListAdapter(getContext(), not_avail_items_list);
                recyclerViewItemsList.setAdapter(notAvailableItemsListAdapter);
            }
        } else if (preferences.getOrderStatus() == 1) {
            if (not_avail_items_list_radius.size() > 0) {
                notAvailableItemsListRadiusAdapter = new NotAvailableItemsListRadiusAdapter(getContext(), not_avail_items_list_radius);
                recyclerViewItemsList.setAdapter(notAvailableItemsListRadiusAdapter);
            }
        } else if (preferences.getOrderStatus() == 3) {
            AvailNotAvailItemsListsFragment.mNotAvailItmes.setText(not_avail_items_selected_store_and_radius.size()+"");
            notAvailableItemsSelectedStoreListAdapter = new NotAvailableItemsSelectedStoreListAdapter(getContext(), not_avail_items_selected_store_and_radius);
            recyclerViewItemsList.setAdapter(notAvailableItemsSelectedStoreListAdapter);
        }

        not_avail_items = productsName.length;
        editor = getActivity().getSharedPreferences("not_avail_items", Context.MODE_PRIVATE).edit();
        editor.putString("not_available", String.valueOf(not_avail_items));
        editor.apply();
        return view;
    }
}
