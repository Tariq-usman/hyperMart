package com.system.user.menwain.fragments.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.DelivieryAddressesAdapter;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentPayLater;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentPayNow;

public class DeliveryAddressFragment extends Fragment implements View.OnClickListener {

    private TextView mTitleView, mConfirmBtn, mPayNow, mPayLater;
    private ImageView mBackView, mAddNewAddress;
    private String[] address = {"Current Location", "Home", "Office"};
    RecyclerView recyclerViewAddress;
    DelivieryAddressesAdapter delivieryAddressesAdapter;
    private CardView mSearchViewAddress;
    private Bundle bundle;
    private SharedPreferences.Editor editor;
    Prefrences prefrences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivey_address, container, false);

        prefrences = new Prefrences(getContext());
        bundle = new Bundle();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mSearchViewAddress = getActivity().findViewById(R.id.search_view);
        mSearchViewAddress.setVisibility(View.INVISIBLE);

        mBackView = getActivity().findViewById(R.id.iv_back);
        mAddNewAddress = view.findViewById(R.id.add_new_address);
        mBackView.setVisibility(View.VISIBLE);
        mConfirmBtn = view.findViewById(R.id.confirm_btn);
        mPayNow = view.findViewById(R.id.pay_now_delivery_adr);
        mPayLater = view.findViewById(R.id.pay_later_delivery_adr);

        mConfirmBtn.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mPayNow.setOnClickListener(this);
        mPayLater.setOnClickListener(this);
        mAddNewAddress.setOnClickListener(this);

        recyclerViewAddress = view.findViewById(R.id.recycler_view_delivery_address);
        recyclerViewAddress.setHasFixedSize(true);
        recyclerViewAddress.setLayoutManager(new LinearLayoutManager(getContext()));

        delivieryAddressesAdapter = new DelivieryAddressesAdapter(address, getContext());
        recyclerViewAddress.setAdapter(delivieryAddressesAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {
        ItemsAvailabilityStoresFragment itemsAvailabilityStoresFragment = new ItemsAvailabilityStoresFragment();
        String backStateName = itemsAvailabilityStoresFragment.getClass().getName();
        int id = view.getId();
        if (id == R.id.pay_now_delivery_adr) {
            DialogFragmentPayNow method = new DialogFragmentPayNow();
            method.show(getFragmentManager(), "pay online");
            bundle.putBoolean("pay_now", true);
            mPayNow.setBackgroundResource(R.drawable.bg_store_btn_colored);
            mPayLater.setBackgroundResource(0);
            mPayNow.setTextColor(Color.parseColor("#FFFFFF"));
            mPayLater.setTextColor(Color.parseColor("#004040"));
        } else if (id == R.id.pay_later_delivery_adr) {
            bundle.putBoolean("pay_later", true);
            mPayLater.setBackgroundResource(R.drawable.bg_store_btn_colored);
            mPayNow.setBackgroundResource(0);
            mPayNow.setTextColor(Color.parseColor("#004040"));
            mPayLater.setTextColor(Color.parseColor("#FFFFFF"));
            DialogFragmentPayLater payInstore = new DialogFragmentPayLater();
            payInstore.show(getFragmentManager(), "pay in store");
        } else if (id == R.id.confirm_btn) {
            prefrences.setFragStatus(2);
            ItemsAvailabilityStoresFragment storesFragment = new ItemsAvailabilityStoresFragment();
            storesFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, storesFragment).addToBackStack(null).commit();

          /*  DialogFragmentPayMethod payMethod = new DialogFragmentPayMethod();
            payMethod.show(getFragmentManager(),"payment method");*/

        }/*else if (id == R.id.add_new_address){
            Intent mapIntent = new Intent(getContext(), MapsActivity.class);
//            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mapIntent);
        }*/ else if (id == R.id.iv_back) {
            prefrences.setFragStatus(0);
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).addToBackStack(null).commit();
            mBackView.setVisibility(View.GONE);
        }

    }
}
