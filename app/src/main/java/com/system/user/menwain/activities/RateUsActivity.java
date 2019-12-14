package com.system.user.menwain.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.MoreFragment;

public class RateUsActivity extends Fragment {

    TextView mConfirmBtn, mTitle;
    ImageView mBackBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_rate_us,container,false);

        mConfirmBtn = view.findViewById(R.id.submit);
        mTitle = getActivity().findViewById(R.id.toolbar_title);
        mBackBtn = getActivity().findViewById(R.id.iv_back);

        mTitle.setText("Rate Us");
//        mBackBtn.setImageResource(R.drawable.ic_backwhite);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();*/
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new MoreFragment()).addToBackStack(null).commit();
                mBackBtn.setVisibility(View.INVISIBLE);
               // finish();
            }
        });

        return view;
    }
}
