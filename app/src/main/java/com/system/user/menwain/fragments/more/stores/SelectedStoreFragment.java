package com.system.user.menwain.fragments.more.stores;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.system.user.menwain.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.category_adapters.FilterItemsAdapter;
import com.system.user.menwain.adapters.more_adapters.FilteredStoresAdapter;
import com.system.user.menwain.adapters.category_adapters.SelectedItemAdapter;
import com.system.user.menwain.adapters.more_adapters.SelectedStoreAdapter;
import com.system.user.menwain.adapters.more_adapters.SelectedStoresNamesAdapter;
import com.system.user.menwain.adapters.more_adapters.StoresAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
    private ImageView ivBackBtnSelectedStore;
    private CardView mSearchViewSelecredStore;
    private RatingBar ratingBar;
    private Prefrences prefrences;


    private String[] storesName = {"Madina", "Metro", "Makro", "Pak", "Alrasheed", "ARY", "Meezan", "Lahore", "ARY", "Meezan", "Lahore"};
    private int[] stores = {R.drawable.madina, R.drawable.metro, R.drawable.makro, R.drawable.pak, R.drawable.alrasheed_cash_carry, R.drawable.ary_cash_carry, R.drawable.meezan, R.drawable.lahore, R.drawable.ary_cash_carry, R.drawable.meezan, R.drawable.lahore};
    private String[] stores_Name = {"Madina c carry", "Metro c carry", "Makro c carry", "Pak c carry", "Alrasheed c carry", "ARY c carry",
            "Meezan c carry", "Lahore c carry", "ARY c carry", "Meezan c carry"};
    private String[] productsName = {"Cooking oil", "Chicken", "Meat", "Butter", "Eggs", "Chocolate", "Frouts", "Carrot", "Mango", "Vegetables"};
    private int[] items = {R.drawable.coocking_oil, R.drawable.chikken, R.drawable.meat, R.drawable.butter, R.drawable.eggs, R.drawable.choclate, R.drawable.frouts,
            R.drawable.carrot, R.drawable.mango, R.drawable.vagitables};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_store, container, false);
        prefrences = new Prefrences(getContext());
        ratingBar = view.findViewById(R.id.rating_bar_selected_stores);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        //stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.colorButton), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getContext().getResources().getColor(R.color.yellowColor), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(getContext().getResources().getColor(R.color.yellowColor), PorterDuff.Mode.SRC_ATOP);

        mSearchViewSelecredStore = getActivity().findViewById(R.id.search_view);
        mSearchViewSelecredStore.setVisibility(View.VISIBLE);
        ivBackBtnSelectedStore = getActivity().findViewById(R.id.iv_back);
        ivBackBtnSelectedStore.setVisibility(View.VISIBLE);
        ivBackBtnSelectedStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreStoresFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new StoresFragment()).addToBackStack(null).commit();
                ivBackBtnSelectedStore.setVisibility(View.INVISIBLE);
            }
        });
        recyclerViewSelectedStore = view.findViewById(R.id.recycler_view_selected_store);
        recyclerViewSelectedStore.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSelectedStore.setLayoutManager(linearLayoutManager);
        recyclerViewSelectedStore.setAdapter(new SelectedStoreAdapter(productsName, getContext(), items));
        int position = StoresAdapter.pos;
        //recyclerViewSelectedStore.smoothScrollToPosition(position + 1);


        /*recyclerViewStoresName = view.findViewById(R.id.recycler_view_selected_stores_name);
        recyclerViewStoresName.setHasFixedSize(true);
        recyclerViewStoresName.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewStoresName.setAdapter(new SelectedStoresNamesAdapter(getContext(), storesName));
*/

        recyclerViewFilterStores = view.findViewById(R.id.recycler_view_filter_filtered);
        recyclerViewFilterStores.setHasFixedSize(true);
        recyclerViewFilterStores.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        recyclerViewFilterStores.setAdapter(new FilteredStoresAdapter(productsName, getContext(), items, stores_Name));

        return view;
    }

}
