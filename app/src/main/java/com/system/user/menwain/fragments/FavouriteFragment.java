package com.system.user.menwain.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.FavouriteAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteFragment extends Fragment {
    private int [] products = {R.drawable.productpic,R.drawable.productdisplay,R.drawable.productpic,R.drawable.productdisplay,R.drawable.productpic,R.drawable.productdisplay};

    RecyclerView recyclerViewFavourite;
    FavouriteAdapter favouriteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite,container,false);

        recyclerViewFavourite = view.findViewById(R.id.recycler_view_favourite);
        recyclerViewFavourite.setHasFixedSize(true);
        recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getContext()));

        favouriteAdapter = new FavouriteAdapter(getContext(),products);
        recyclerViewFavourite.setAdapter(favouriteAdapter);
        return view;
    }
}
