package com.system.user.menwain.utils;

import android.app.ProgressDialog;
import android.content.Context;

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

    public static final ProgressDialog progressDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Fetching max value
        progressDialog.getMax();
        return progressDialog;
    }
}
