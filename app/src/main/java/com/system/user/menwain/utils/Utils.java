package com.system.user.menwain.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.system.user.menwain.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static final String phone_n_pattern = "-?(0|[1-9]\\d*)";

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" + //a lower case letter must occur at least once
                "(?=.*[A-Z])" + //an upper case letter must occur at least once
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static Dialog dialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_loading_dialog);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setCancelable(true);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    public static Toast toast(VolleyError error,Context context) {

        try {
            if (error instanceof TimeoutError) {
                Toast.makeText(context, context.getString(R.string.network_timeout), Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(context, context.getString(R.string.authentication_error), Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(context, context.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                Toast.makeText(context, context.getString(R.string.no_network_found), Toast.LENGTH_LONG).show();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toast(error,context);
    }

}
