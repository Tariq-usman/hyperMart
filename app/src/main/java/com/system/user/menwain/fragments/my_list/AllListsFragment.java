package com.system.user.menwain.fragments.my_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.my_lists_adapters.AllListsAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllListsFragment extends Fragment {

    private String [] listsName = {"List 1","List 2","List 3","List 4","List 5","List 6","List 1","List 2","List 3"};

    RecyclerView recyclerViewAllLists;
    AllListsAdapter allListsAdapter;
    private CardView mSearchViewAllLists;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_lists,container,false);

        mSearchViewAllLists = getActivity().findViewById(R.id.search_view);
        mSearchViewAllLists.setVisibility(View.INVISIBLE);

        recyclerViewAllLists = view.findViewById(R.id.recycler_view_all_lists);
        recyclerViewAllLists.setHasFixedSize(true);
        recyclerViewAllLists.setLayoutManager(new LinearLayoutManager(getContext()));

        allListsAdapter = new AllListsAdapter(listsName,getContext());
        recyclerViewAllLists.setAdapter(allListsAdapter);
        return view;
    }
}
