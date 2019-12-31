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

}