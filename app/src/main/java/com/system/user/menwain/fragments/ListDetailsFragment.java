package com.system.user.menwain.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.ListDetailsAdapter;
import com.system.user.menwain.fragments.AllListsFragment;

public class ListDetailsFragment extends Fragment implements View.OnClickListener {
    private int[] products = {R.drawable.productpic, R.drawable.productdisplay, R.drawable.productpic, R.drawable.productdisplay};
    private RecyclerView recyclerViewListDetails;
    private ListDetailsAdapter listDetailsAdapter;
    private FloatingActionButton floatingActionButton;
    private TextView mConfirmBtn,mTitleListDetails;
    private ImageView mBackBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_details,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mTitleListDetails= getActivity().findViewById(R.id.toolbar_title);
        mTitleListDetails.setText("Items Details");
        mConfirmBtn = view.findViewById(R.id.confirm_btn_list_details);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mBackBtn.setVisibility(View.VISIBLE);

        //floatingActionButton.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);

        recyclerViewListDetails = view.findViewById(R.id.recycler_view_list_details);
        recyclerViewListDetails.setHasFixedSize(true);
        recyclerViewListDetails.setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));
        listDetailsAdapter = new ListDetailsAdapter(getContext(), products);
        recyclerViewListDetails.setAdapter(listDetailsAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.confirm_btn_list_details) {
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new AllListsFragment()).addToBackStack(null).commit();
            mTitleListDetails.setText("Lists");
            mBackBtn.setVisibility(View.INVISIBLE);
        } else if (id == R.id.iv_back) {
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new AllListsFragment()).addToBackStack(null).commit();
            mTitleListDetails.setText("Lists");
            mBackBtn.setVisibility(View.INVISIBLE);
        }

    }
}
