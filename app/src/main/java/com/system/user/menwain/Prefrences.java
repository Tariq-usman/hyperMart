package com.system.user.menwain;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefrences {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Context context;
    Object o;

    // Constructor
    public Prefrences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("fragment_status", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setFragStatus(int fragStatus) {
        editor.putInt("frag_status", fragStatus);
        editor.apply();
        editor.commit();
    }

    public int getFragStatus() {
        return sharedPreferences.getInt("frag_status", 0);
    }

    public void setPaymentStatus(int paymentStatus){
        editor.putInt("payment_status", paymentStatus);
        editor.apply();
        editor.commit();
    }
    public int getPaymentStatus() {
        return sharedPreferences.getInt("payment_status", 0);
    }
    public void setPayLaterStatus(int paymentStatus){
        editor.putInt("pay_later_checked", paymentStatus);
        editor.apply();
        editor.commit();
    }
    public int getPayLateStatus() {
        return sharedPreferences.getInt("pay_later_checked", 0);
    }
    public void setPayNowStatus(int paymentStatus){
        editor.putInt("pay_now_checked", paymentStatus);
        editor.apply();
        editor.commit();
    }
    public int getPayNowStatus() {
        return sharedPreferences.getInt("pay_now_checked", 0);
    }
}