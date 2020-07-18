package com.system.user.menwain.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.system.user.menwain.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static final String phone_n_pattern = "-?(0|[1-9]\\d*)";

    public static    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static  boolean isValidPassword(final String password) {

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
}
