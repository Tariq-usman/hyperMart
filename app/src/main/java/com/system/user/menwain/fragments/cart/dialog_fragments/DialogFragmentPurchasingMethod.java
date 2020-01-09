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

import com.system.user.menwain.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.cart.AvailNotAvailItemsListsFragment;
import com.system.user.menwain.fragments.cart.CartFragment;
import com.system.user.menwain.fragments.cart.PaymentFragment;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentPurchasingMethod extends DialogFragment implements View.OnClickListener {
    TextView mConfirm, mTitleView, tvDateInStorePurchase;
    ImageView mCloseBtn;
    private int mYear, mMonth, mDay;
    Prefrences prefrences;
    private int pay_status;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_purchasing_method, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        prefrences = new Prefrences(getContext());
        pay_status = prefrences.getPaymentStatus();
        mConfirm = view.findViewById(R.id.confirm_btn_purchasing_method);
        mTitleView = view.findViewById(R.id.title_view);
        mCloseBtn = view.findViewById(R.id.close_back_view);

        mTitleView.setText("In Store Purchase");
        mCloseBtn.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.confirm_btn_purchasing_method) {
            if (pay_status == 1) {
                prefrences.setCartFragStatus(4);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new PaymentFragment()).commit();
                dismiss();
            } else if (pay_status == 2) {
                prefrences.setCartFragStatus(0);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).commit();
//            startActivity(new Intent(getContext(), PaymentFragment.class));
                dismiss();
            }
        } else if (id == R.id.close_back_view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AvailNotAvailItemsListsFragment()).commit();
//            DialogFragmentDeliveryTime dialogFragmentDeliveryTime = new DialogFragmentDeliveryTime();
//            dialogFragmentDeliveryTime.show(getFragmentManager(), "Delivery time");
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