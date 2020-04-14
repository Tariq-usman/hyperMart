package com.system.user.menwain.fragments.cart.dialog_fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.cart.CartFragment;
import com.system.user.menwain.fragments.more.orders.OrdersFragment;
import com.system.user.menwain.fragments.my_list.AllListsFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONArray;

public class DialogFragmentSaveList extends DialogFragment implements View.OnClickListener {
    TextView mConfirm, mTitleView, tvDateInStorePurchase;


    private ImageView mCart, mFavourite, mHome, mCategory, mMore,mCloseBtn, mBackBtnPay;
    private TextView totalCartQuantity, mActionBarTitle, tvHome, tvCategory, tvCart, tvMore, tvFavourite;
    List<String> list = new ArrayList<>();

    private int mYear, mMonth, mDay;
    Prefrences prefrences;
    private int pay_status;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_purchasing_method, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        prefrences = new Prefrences(getContext());
        mConfirm = view.findViewById(R.id.confirm_btn_purchasing_method);
        mCloseBtn = view.findViewById(R.id.close_back_view);
        mBackBtnPay = getActivity().findViewById(R.id.iv_back);
        mBackBtnPay.setVisibility(View.VISIBLE);

        mHome = getActivity().findViewById(R.id.home_view);
        tvHome = getActivity().findViewById(R.id.tv_home_view);
        mCategory = getActivity().findViewById(R.id.category_view);
        tvCategory = getActivity().findViewById(R.id.tv_category_view);

        mCart = getActivity().findViewById(R.id.cart);
        tvCart = getActivity().findViewById(R.id.tv_cart);

        mFavourite = getActivity().findViewById(R.id.favourite_view);
        tvFavourite = getActivity().findViewById(R.id.tv_favourite_view);

        mMore = getActivity().findViewById(R.id.more);
        tvMore = getActivity().findViewById(R.id.tv_more);

        mCloseBtn.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.confirm_btn_purchasing_method) {
            pay_status = prefrences.getPayRBtnStatus();
            if (pay_status == 5) {
                prefrences.setCartFragStatus(0);
                prefrences.setBottomNavStatus(4);
                mHome.setImageResource(R.drawable.ic_housewhite);
                tvHome.setTextColor(Color.parseColor("#004040"));
                mCategory.setImageResource(R.drawable.ic_searchwhite);
                tvCategory.setTextColor(Color.parseColor("#004040"));
                mFavourite.setImageResource(R.drawable.ic_likeblue);
                tvFavourite.setTextColor(Color.parseColor("#00c1bd"));
                mCart.setImageResource(R.drawable.ic_cart_white);
                tvCart.setTextColor(Color.parseColor("#004040"));
                mMore.setImageResource(R.drawable.ic_morewhite);
                tvMore.setTextColor(Color.parseColor("#004040"));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AllListsFragment()).commit();
                mBackBtnPay.setVisibility(View.INVISIBLE);
            } else {
                prefrences.setCartFragStatus(0);
                prefrences.setBottomNavStatus(5);
                prefrences.setMorFragStatus(2);
                prefrences.setMoreOrdersFragStatus(1);
                mHome.setImageResource(R.drawable.ic_housewhite);
                tvHome.setTextColor(Color.parseColor("#004040"));
                mCategory.setImageResource(R.drawable.ic_searchwhite);
                tvCategory.setTextColor(Color.parseColor("#004040"));
                mFavourite.setImageResource(R.drawable.ic_likewhite);
                tvFavourite.setTextColor(Color.parseColor("#004040"));
                mCart.setImageResource(R.drawable.ic_cart_white);
                tvCart.setTextColor(Color.parseColor("#004040"));
                mMore.setImageResource(R.drawable.ic_moreblue);
                tvMore.setTextColor(Color.parseColor("#00c1bd"));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).commit();
                mBackBtnPay.setVisibility(View.INVISIBLE);
            }
            dismiss();

        } else if (id == R.id.close_back_view) {
            prefrences.setCartFragStatus(0);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).commit();
            mBackBtnPay.setVisibility(View.INVISIBLE);
            dismiss();
        }

    }

    private void pickDate() {
        Calendar calendar = Calendar.getInstance();
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvDateInStorePurchase.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mYear);
        datePickerDialog.setTitle("Select Date..");
        datePickerDialog.show();
    }
}
