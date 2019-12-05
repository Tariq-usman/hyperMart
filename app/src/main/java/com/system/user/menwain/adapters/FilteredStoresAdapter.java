package com.system.user.menwain.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.SelectedStoreFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class FilteredStoresAdapter extends RecyclerView.Adapter<FilteredStoresAdapter.FilterStoresViewHolder> {

    private String[] storesName;
    int[] stores;
    Context context;

    public FilteredStoresAdapter(String[] storesName, Context context, int[] stores) {
        this.storesName = storesName;
        this.stores = stores;
        this.context = context;
    }

    @NonNull
    @Override
    public FilterStoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filtered_stores, parent, false);
        FilterStoresViewHolder categoryViewHolder = new FilterStoresViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterStoresViewHolder holder, final int position) {
        holder.mFilterStoreNameView.setText(storesName[position]);
        holder.mFilterStore.setImageResource(stores[position]);
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                Log.e("check", position + "");
                pos = position;
//                             FragmentManager fragmentManager =   ((AppCompatActivity)context).getSupportFragmentManager();
//                             fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new ItemsFragment()).addToBackStack(null).commit();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new SelectedStoreFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return storesName.length;
    }

    public static class FilterStoresViewHolder extends RecyclerView.ViewHolder {
        private TextView mFilterStoreNameView;
        private CircleImageView mFilterStore;

        public FilterStoresViewHolder(@NonNull View itemView) {
            super(itemView);
            mFilterStoreNameView = itemView.findViewById(R.id.filter_store_name_view);
            mFilterStore = itemView.findViewById(R.id.filter_stores_image_view);
        }
    }
}
