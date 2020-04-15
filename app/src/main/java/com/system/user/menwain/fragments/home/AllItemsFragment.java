package com.system.user.menwain.fragments.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.adapters.home_adapters.grid_adapters.ShopItemsGridAdapter;
import com.system.user.menwain.adapters.home_adapters.list_adapters.ShopItemsListAdapter;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.home_adapters.grid_adapters.ExploreItemsGridAdapter;
import com.system.user.menwain.adapters.home_adapters.list_adapters.ExploreItemsListAdapter;
import com.system.user.menwain.adapters.home_adapters.grid_adapters.ExploreShopItemsGridAdapter;
import com.system.user.menwain.adapters.home_adapters.list_adapters.ExploreShopItemsListAdapter;

public class AllItemsFragment extends Fragment implements View.OnClickListener {
    private String[] productsName = {"Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallaxy S8", "Samsung", "BlockBuster", "Gallexy M30",
            "Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallexy M30s", "Samsung s4"};
    private int[] products = {R.drawable.p, R.drawable.pict, R.drawable.pictu, R.drawable.picturep,
            R.drawable.picturepi, R.drawable.picturepic, R.drawable.p, R.drawable.pict, R.drawable.pictu,
            R.drawable.picturep, R.drawable.picturepi, R.drawable.picturepic};


    private String[] productsName1 = {"Cooking oil", "Chicken", "Meat", "Butter", "Eggs", "Chocolate", "Frouts", "Carrot", "Mango", "Vegetables"};
    private int[] items = {R.drawable.coocking_oil, R.drawable.chikken, R.drawable.meat, R.drawable.butter, R.drawable.eggs, R.drawable.choclate, R.drawable.frouts,
            R.drawable.carrot, R.drawable.mango, R.drawable.vagitables};

    private RecyclerView recyclerViewAllitem;
    private LinearLayoutManager linearLayoutManager;
    private ImageView ivGridListView, ivBack, ivListGridView, ivMenu;
    private boolean isList = false;
    private Intent intent;
    private String val;
    private int val1;
    private int val2;
    Bundle bundle;
    Toolbar toolbar;
    private TextView fragTitle;
    private CardView searchViewAllItems;
    Prefrences prefrences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_items, container, false);
        prefrences = new Prefrences(getContext());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        searchViewAllItems = getActivity().findViewById(R.id.search_view);
        ivListGridView = getActivity().findViewById(R.id.iv_grid_list_view);
        ivListGridView.setVisibility(View.VISIBLE);
        ivListGridView.setImageResource(R.drawable.ic_list_view);
        ivListGridView.setOnClickListener(this);


        ivBack = getActivity().findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        ivBack.setVisibility(View.VISIBLE);


        bundle = this.getArguments();
        val = bundle.getString("explore", "");
        Log.i("val", String.valueOf(val));
        Log.i("val1", String.valueOf(val1));
        recyclerViewAllitem = view.findViewById(R.id.recycler_view_grid_items);
        if (val.equals("1")) {
            recyclerViewAllitem.setHasFixedSize(true);
            recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerViewAllitem.setAdapter(new ExploreShopItemsGridAdapter(getContext(), productsName, products));
        } else if (val.equals("2")) {
            recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerViewAllitem.setAdapter(new ExploreItemsGridAdapter(getContext(), productsName1, items));
        } else if (val.equals("3")) {
            recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerViewAllitem.setAdapter(new ShopItemsGridAdapter(getContext(), productsName, products));
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                prefrences.setHomeFragStatus(0);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(),"Home").addToBackStack(null).commit();
                ivBack.setVisibility(View.INVISIBLE);
                ivListGridView.setVisibility(View.INVISIBLE);

                break;
            case R.id.iv_grid_list_view:
                if (val.equals("1")) {
                    if (isList == false) {
                        isList = true;
                        ivListGridView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreShopItemsListAdapter(getContext(), productsName, products));

                    } else {
                        isList = false;
                        ivListGridView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreShopItemsGridAdapter(getContext(), productsName, products));
                    }
                } else if (val.equals("2")) {
                    if (isList == false) {
                        isList = true;
                        ivListGridView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreItemsListAdapter(getContext(), productsName1, items));

                    } else {
                        isList = false;
                        ivListGridView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreItemsGridAdapter(getContext(), productsName1, items));
                    }
                } else if (val.equals("3")) {
                    if (isList == false) {
                        isList = true;
                        ivListGridView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ShopItemsListAdapter(getContext(), productsName, products));

                    } else {
                        isList = false;
                        ivListGridView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ShopItemsGridAdapter(getContext(), productsName, products));
                    }
                }
                break;

        }

    }
}
