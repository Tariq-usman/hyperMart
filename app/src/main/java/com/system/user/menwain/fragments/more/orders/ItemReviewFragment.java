package com.system.user.menwain.fragments.more.orders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;

public class ItemReviewFragment extends Fragment {

    private TextView mSubmitReview, mTitle;
    private ImageView mBackBtn;
    private Prefrences prefrences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_item_review, container, false);
        prefrences = new Prefrences(getContext());
        mSubmitReview = view.findViewById(R.id.submit_review);
        //mTitle = getActivity().findViewById(R.id.title_view);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mBackBtn.setVisibility(View.VISIBLE);

//        mTitle.setText("Item Review");

        mSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrderDetailsFragment()).addToBackStack(null).commit();
                mBackBtn.setVisibility(View.INVISIBLE);
//                startActivity(new Intent(getContext(),RateUsFragment.class));
//                finish();
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreOrdersFragStatus(2);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrderDetailsFragment()).addToBackStack(null).commit();
                mBackBtn.setVisibility(View.INVISIBLE);
//                finish();
            }
        });
        return view;
    }
}
