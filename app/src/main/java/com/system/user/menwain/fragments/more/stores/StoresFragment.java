package com.system.user.menwain.fragments.more.stores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.StoresAdapter;
import com.system.user.menwain.fragments.more.MoreFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StoresFragment extends Fragment {
    private String[] productsName = {"Madina", "Metro", "Makro", "Pak", "Alrasheed", "ARY", "Meezan", "Lahore"};
    private int[] stores = {R.drawable.madina, R.drawable.metro, R.drawable.makro, R.drawable.pak, R.drawable.alrasheed_cash_carry, R.drawable.ary_cash_carry, R.drawable.meezan, R.drawable.lahore};
    RecyclerView recyclerViewProductCategory;
    StoresAdapter storesAdapter;
    private ImageView ivBackStores;
    private TextView tvTitleStores;
    private CardView mSearchStores;
    private Prefrences prefrences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores, container, false);
        prefrences = new Prefrences(getContext());
        ivBackStores = getActivity().findViewById(R.id.iv_back);
        ivBackStores.setVisibility(View.VISIBLE);
        mSearchStores = getActivity().findViewById(R.id.search_view);
        mSearchStores.setVisibility(View.VISIBLE);
        ivBackStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreStoresFragStatus(0);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new MoreFragment()).addToBackStack(null).commit();
                ivBackStores.setVisibility(View.INVISIBLE);
             //   tvTitleStores.setText("More");
            }
        });
        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_stores);
        recyclerViewProductCategory.setHasFixedSize(true);
        recyclerViewProductCategory.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        recyclerViewProductCategory.setAdapter(new StoresAdapter(getContext(), productsName, stores));

        return view;
    }
}
