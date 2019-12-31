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

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.DeliveryTimesAdapter;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentPurchasingMethod;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DialogFragmentDeliveryTime extends DialogFragment implements View.OnClickListener {
    private int mYear, mMonth, mDay;
    TextView mConfirm,mTitleView,tvDate;
    ImageView mCloseBtn;
    private RecyclerView recyclerView;
    private String [] delivery_times = {"10:00 - 11:30","11:30 - 12:00","1:30 - 2:30","04:00 - 06:00"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_delivery_time,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mConfirm = view.findViewById(R.id.confirm_btn_delivery_time);
        mTitleView = view.findViewById(R.id.title_view);
        mCloseBtn = view.findViewById(R.id.close_back_view);
        recyclerView = view.findViewById(R.id.recycler_view_delivery_time);
        tvDate = view.findViewById(R.id.tv_date);

        mTitleView.setText("Delivery time");
        mConfirm.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
        tvDate.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(new DeliveryTimesAdapter(getContext(),delivery_times));
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirm_btn_delivery_time:
                DialogFragmentPurchasingMethod dialogFragmentPurchasingMethod = new DialogFragmentPurchasingMethod();
                dialogFragmentPurchasingMethod.show(getFragmentManager(),"Purchasing Method");
                dismiss();
                break;
            case R.id.tv_date:
                getDate();
                break;
            case R.id.close_back_view:
                dismiss();
                break;
        }
    }

    private void getDate() {
        // Get Current Date
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tvDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("Select Date..");
        datePickerDialog.show();
    }

}
