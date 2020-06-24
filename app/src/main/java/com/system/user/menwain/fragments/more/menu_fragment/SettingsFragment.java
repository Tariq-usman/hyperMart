package com.system.user.menwain.fragments.more.menu_fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.system.user.menwain.R;
import com.system.user.menwain.activities.MainActivity;
import com.system.user.menwain.custom_languages.LocaleManager;
import com.system.user.menwain.fragments.more.MoreFragment;
import com.system.user.menwain.others.Preferences;

public class SettingsFragment extends Fragment {
    private TextView changeLanguage;
    private ImageView ivBack;
    private Preferences preferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        preferences = new Preferences(getContext());
        ivBack = view.findViewById(R.id.iv_back_settings);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new MoreFragment()).addToBackStack(null).commit();

            }
        });
        changeLanguage = view.findViewById(R.id.change_language);
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectLangaugeDialog();
            }
        });
        return view;
    }
    private void showSelectLangaugeDialog() {
        final String[] langaugesList = {"English", "عربى"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Language");
        builder.setSingleChoiceItems(langaugesList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i) {
                    case 0:
                        setNewLocale((AppCompatActivity) getContext(), LocaleManager.ENGLISH);
                        preferences.setLanguage("English");
//                        getActivity().recreate();
                        break;
                    case 1:
                        setNewLocale((AppCompatActivity)getContext(), LocaleManager.ARABIC);
                        preferences.setLanguage("Arabic");
                        // getActivity().recreate();
                        break;
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void setNewLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(mContext, language);
        Intent intent = getActivity().getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

}
