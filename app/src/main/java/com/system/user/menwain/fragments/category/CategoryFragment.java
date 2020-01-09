package com.system.user.menwain.fragments.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class CategoryFragment extends Fragment {
    TextView mProductsCategory, mProductsStores;
    private ImageView mBarCodeScanner;
    private Button btn;
    private int CAMREA_CODE = 1;
    private CardView mSearchViewCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_stores, container, false);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.products_container, new ItemsCategoryFragment()).addToBackStack(null).commit();
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

        return view;
    }
}
