package com.system.user.menwain.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.system.user.menwain.R;
import com.system.user.menwain.custom_languages.BaseActivity;
import com.system.user.menwain.custom_languages.LocalHelper;
import com.system.user.menwain.custom_languages.LocaleManager;
import com.system.user.menwain.others.Preferences;

public class SplashScreen extends BaseActivity {
    private String langauge;
    SharedPreferences preferences;
    SharedPreferences.Editor editor, editor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        preferences = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        langauge = preferences.getString("my_lang", "");

        if (langauge.isEmpty()) {
            showSelectLangaugeDialog();
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }, 3000);
        }
        /*
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
    }
    private void showSelectLangaugeDialog() {
        final String[] langaugesList = {"English", "عربى"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Language");
        builder.setSingleChoiceItems(langaugesList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i) {
                    case 0:
                        editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
                        editor.putString("my_lang", String.valueOf(i));
                        editor.apply();
                        setNewLocale((AppCompatActivity)context, LocaleManager.ENGLISH);
                        recreate();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }, 3000);
                        break;
                    case 1:
                        editor1 = getSharedPreferences("settings", MODE_PRIVATE).edit();
                        editor1.putString("my_lang", String.valueOf(i));
                        editor1.apply();
                        setNewLocale((AppCompatActivity)context, LocaleManager.ARABIC);
                        recreate();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }, 3000);
                        break;
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setNewLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = this.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
