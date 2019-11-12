package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.ItemsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectedItemAdapter extends RecyclerView.Adapter<SelectedItemAdapter.SelectedItemViewHolder> {
    private String [] productsName;
Context context;
    public SelectedItemAdapter(String[] productsName) {
        this.productsName = productsName;
    }

    @NonNull
    @Override
    public SelectedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_items,parent,false);
        SelectedItemViewHolder categoryViewHolder = new SelectedItemViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemViewHolder holder, int position) {
        holder.mProductNameView.setText(productsName[position]);
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
        return productsName.length;
    }

    public static class SelectedItemViewHolder extends RecyclerView.ViewHolder{
        TextView mProductNameView;
        public SelectedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductNameView=itemView.findViewById(R.id.product_name_view);
        }
    }
}
