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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private SharedPreferences.Editor editor;
    Prefrences prefrences;
    private RadioButton rbDeliverToAddress, rbPreparePickUp, rbCashOnDelivery, rbPreparePickFromStore, rbWalkInStore;
    private RadioGroup rgPayNow, rgPayLater;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivey_address, container, false);
        prefrences = new Prefrences(getContext());
        prefrences.setPaymentStatus(1);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mSearchViewAddress = getActivity().findViewById(R.id.search_view);
        mSearchViewAddress.setVisibility(View.INVISIBLE);

        rbCashOnDelivery = view.findViewById(R.id.rb_cash_on_delivery);
        rbCashOnDelivery.setOnClickListener(this);
        rbPreparePickFromStore = view.findViewById(R.id.rb_prepare_and_pick);
        rbPreparePickFromStore.setOnClickListener(this);
        rbWalkInStore = view.findViewById(R.id.rb_Walk_in_tore);
        rbWalkInStore.setOnClickListener(this);

        rgPayNow = view.findViewById(R.id.rg_pay_now);
        rgPayLater = view.findViewById(R.id.rg_pay_later);
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

    private void setRadioButtonChecked() {
        int rBtnStatus = prefrences.getPayLateStatus();
        if (rBtnStatus == 1) {
            prefrences.setPayLaterStatus(1);
            rbWalkInStore.setChecked(false);
            rbPreparePickFromStore.setChecked(false);
            rbCashOnDelivery.setChecked(true);
        } else if (rBtnStatus == 2) {
            prefrences.setPayLaterStatus(2);
            rbWalkInStore.setChecked(false);
            rbPreparePickFromStore.setChecked(true);
            rbCashOnDelivery.setChecked(false);
        } else if (rBtnStatus == 3) {
            prefrences.setPayLaterStatus(3);
            rbWalkInStore.setChecked(true);
            rbPreparePickFromStore.setChecked(false);
            rbCashOnDelivery.setChecked(false);
        }
    }

    @Override
    public void onClick(View view) {
        ItemsAvailabilityStoresFragment itemsAvailabilityStoresFragment = new ItemsAvailabilityStoresFragment();
        String backStateName = itemsAvailabilityStoresFragment.getClass().getName();
        int id = view.getId();
        if (id == R.id.pay_now_delivery_adr) {
            rgPayNow.setVisibility(View.VISIBLE);
            rgPayLater.setVisibility(View.GONE);
            prefrences.setPaymentStatus(1);
            mPayNow.setBackgroundResource(R.drawable.bg_store_btn_colored);
            mPayLater.setBackgroundResource(0);
            mPayNow.setTextColor(Color.parseColor("#FFFFFF"));
            mPayLater.setTextColor(Color.parseColor("#004040"));
        } else if (id == R.id.pay_later_delivery_adr) {
            setRadioButtonChecked();
            rgPayNow.setVisibility(View.GONE);
            rgPayLater.setVisibility(View.VISIBLE);
            prefrences.setPaymentStatus(2);
            mPayLater.setBackgroundResource(R.drawable.bg_store_btn_colored);
            mPayNow.setBackgroundResource(0);
            mPayNow.setTextColor(Color.parseColor("#004040"));
            mPayLater.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (id == R.id.rb_cash_on_delivery) {
            prefrences.setPayLaterStatus(1);
            rbWalkInStore.setChecked(false);
            rbPreparePickFromStore.setChecked(false);
            rbCashOnDelivery.setChecked(true);
        } else if (id == R.id.rb_prepare_and_pick) {
            prefrences.setPayLaterStatus(2);
            rbWalkInStore.setChecked(false);
            rbPreparePickFromStore.setChecked(true);
            rbCashOnDelivery.setChecked(false);
        } else if (id == R.id.rb_Walk_in_tore) {
            prefrences.setPayLaterStatus(3);
            rbWalkInStore.setChecked(true);
            rbPreparePickFromStore.setChecked(false);
            rbCashOnDelivery.setChecked(false);
        } else if (id == R.id.confirm_btn) {
            prefrences.setFragStatus(2);
            ItemsAvailabilityStoresFragment storesFragment = new ItemsAvailabilityStoresFragment();
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, storesFragment).addToBackStack(null).commit();
        } else if (id == R.id.iv_back) {
            prefrences.setFragStatus(0);
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).addToBackStack(null).commit();
            mBackView.setVisibility(View.GONE);
        }

    }
}
