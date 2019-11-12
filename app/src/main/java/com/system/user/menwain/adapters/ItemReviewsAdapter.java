package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemReviewsAdapter extends RecyclerView.Adapter<ItemReviewsAdapter.ItemReviewsViewHolder> {
    private String [] usersName;

    public ItemReviewsAdapter(String[] usersName) {
        this.usersName = usersName;
    }

    @NonNull
    @Override
    public ItemReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_reviews,parent,false);
        ItemReviewsViewHolder categoryViewHolder = new ItemReviewsViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemReviewsViewHolder holder, int position) {
        holder.mUserName.setText(usersName[position]);
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                             FragmentManager fragmentManager =   ((AppCompatActivity)context).getSupportFragmentManager();
                             fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new ItemsFragment()).addToBackStack(null).commit();
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new ItemsFragment();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return usersName.length;
    }

    public static class ItemReviewsViewHolder extends RecyclerView.ViewHolder{
        TextView mUserName;
        public ItemReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName=itemView.findViewById(R.id.user_name_view);
        }
    }
}
