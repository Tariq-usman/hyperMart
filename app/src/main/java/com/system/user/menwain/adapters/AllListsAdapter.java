package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllListsAdapter extends RecyclerView.Adapter<AllListsAdapter.AllListsViewHolder> {

    private String listaName [];
    Context context;
    public AllListsAdapter(String[] listsName, Context context) {
        this.listaName = listsName;
        this.context = context;
    }

    @NonNull
    @Override
    public AllListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_lists,parent,false);
        AllListsViewHolder allListsViewHolder = new AllListsViewHolder(view);
        return allListsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllListsViewHolder holder, int position) {
        holder.mListName.setText(listaName[position]);
    }

    @Override
    public int getItemCount() {
        return listaName.length;
    }

    public static class AllListsViewHolder extends RecyclerView.ViewHolder{
        TextView mListName;
        public AllListsViewHolder(@NonNull View itemView) {
            super(itemView);
            mListName = itemView.findViewById(R.id.list_name_view);
        }
    }
}
