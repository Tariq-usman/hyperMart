package com.system.user.menwain.fragments.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.category_adapters.CategoryAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsCategoryFragment extends Fragment {
    private String [] productsName={"Cooking oil","Chicken","Meat","Butter","Eggs","Chocolate","Frouts","Carrot","Mango","Vegetables"};
    private int [] items = {R.drawable.coocking_oil,R.drawable.chikken,R.drawable.meat,R.drawable.butter,R.drawable.eggs,R.drawable.choclate,R.drawable.frouts,
            R.drawable.carrot,R.drawable.mango,R.drawable.vagitables};
    RecyclerView recyclerViewProductCategory;
    CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_category,container,false);

        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_category);
        recyclerViewProductCategory.setHasFixedSize(true);
        recyclerViewProductCategory.setLayoutManager(new GridLayoutManager(getContext(),3, GridLayoutManager.VERTICAL,false));
        recyclerViewProductCategory.setAdapter(new CategoryAdapter(productsName,items));

        return view;
    }
}
