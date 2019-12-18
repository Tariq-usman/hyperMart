package com.system.user.menwain.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.DelivieryAddressesAdapter;
import com.system.user.menwain.fragments.CartFragment;
import com.system.user.menwain.fragments.ItemsAvailabilityStoresFragment;

public class DeliveryAddressFragment extends Fragment implements View.OnClickListener {

    private TextView mTitleView, mConfirmBtn;
    private ImageView mBackView,mMenu;
    private String [] address = {"Home","Office"};
    RecyclerView recyclerViewAddress;
    DelivieryAddressesAdapter delivieryAddressesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivey_address,container,false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mTitleView = getActivity().findViewById(R.id.toolbar_title);
      /*  mMenu = getActivity().findViewById(R.id.iv_open_drawer);
        mMenu.setVisibility(View.GONE);*/
        mBackView = getActivity().findViewById(R.id.iv_back);
        mBackView.setVisibility(View.VISIBLE);
        mConfirmBtn = view.findViewById(R.id.confirm_btn);

        mTitleView.setText("Choose Location");
        mConfirmBtn.setOnClickListener(this);
        mBackView.setOnClickListener(this);

        recyclerViewAddress = view.findViewById(R.id.recycler_view_delivery_address);
        recyclerViewAddress.setHasFixedSize(true);
        recyclerViewAddress.setLayoutManager(new LinearLayoutManager(getContext()));

        delivieryAddressesAdapter = new DelivieryAddressesAdapter(address,getContext());
        recyclerViewAddress.setAdapter(delivieryAddressesAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {
        ItemsAvailabilityStoresFragment itemsAvailabilityStoresFragment = new ItemsAvailabilityStoresFragment();
        String backStateName = itemsAvailabilityStoresFragment.getClass().getName();
        int id= view.getId();

        if (id==R.id.confirm_btn){
            DialogFragmentPayMethod payMethod = new DialogFragmentPayMethod();
            payMethod.show(getFragmentManager(),"payment method");
            //getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,itemsAvailabilityStoresFragment).addToBackStack(backStateName).commit();
//            startActivity(new Intent(getContext(), ItemsAvailabilityStoresFragment.class));
//            getActivity().finish();
        }else if (id == R.id.iv_back){
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).addToBackStack(null).commit();
//            mMenu.setVisibility(View.VISIBLE);
            mTitleView.setText("Cart");
            mBackView.setVisibility(View.GONE);
        }

    }
}
