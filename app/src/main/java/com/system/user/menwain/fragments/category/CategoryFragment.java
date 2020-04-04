package com.system.user.menwain.fragments.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.category_adapters.CategoryAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryFragment extends Fragment {
    private String [] productsName={"Cooking oil","Chicken","Meat","Butter","Eggs","Chocolate","Frouts","Carrot","Mango","Vegetables"};
    private int [] items = {R.drawable.coocking_oil,R.drawable.chikken,R.drawable.meat,R.drawable.butter,R.drawable.eggs,R.drawable.choclate,R.drawable.frouts,
            R.drawable.carrot,R.drawable.mango,R.drawable.vagitables};
    RecyclerView recyclerViewProductCategory;
    CategoryAdapter categoryAdapter;
    private ImageView mBarCodeScanner;
    private CardView mSearchViewCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_category,container,false);
        mSearchViewCategory = getActivity().findViewById(R.id.search_view);
        mSearchViewCategory.setVisibility(View.VISIBLE);

        mBarCodeScanner = getActivity().findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });
        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_category);
        recyclerViewProductCategory.setHasFixedSize(true);
        recyclerViewProductCategory.setLayoutManager(new GridLayoutManager(getContext(),3, GridLayoutManager.VERTICAL,false));
        recyclerViewProductCategory.setAdapter(new CategoryAdapter(getContext(),productsName,items));

        return view;
    }
}
