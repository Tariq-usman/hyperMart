package com.system.user.menwain.fragments.others;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.ItemReviewsAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemReviewsFragment extends Fragment {

    RecyclerView recyclerViewItemReviews;
    ItemReviewsAdapter itemReviewsAdapter;

    String [] usersName= {"M arslan","M nabeel","Ahmed idrees","M arslan","M nabeel","Ahmed idrees","M arslan","M nabeel","Ahmed idrees"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_item_reviews,container,false);

        recyclerViewItemReviews = view.findViewById(R.id.recycler_view_reviews);
        recyclerViewItemReviews.setHasFixedSize(true);
        recyclerViewItemReviews.setLayoutManager(new LinearLayoutManager(getContext()));

        itemReviewsAdapter = new ItemReviewsAdapter(usersName);
        recyclerViewItemReviews.setAdapter(itemReviewsAdapter);
        return view;
    }
}
