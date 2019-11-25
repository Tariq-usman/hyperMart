package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder> {
    private String [] productsName;
    int [] stores;
    Context context;
    public StoresAdapter(Context context, String[] productsName, int[] stores) {
        this.productsName = productsName;
        this.context = context;
        this.stores = stores;
    }

    @NonNull
    @Override
    public StoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_stores,parent,false);
        StoresViewHolder categoryViewHolder = new StoresViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoresViewHolder holder, int position) {
        holder.mProductNameView.setText(productsName[position]);
        holder.mStore.setImageResource(stores[position]);
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class StoresViewHolder extends RecyclerView.ViewHolder{
        TextView mProductNameView;
        CircleImageView mStore;
        public StoresViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductNameView=itemView.findViewById(R.id.store_name_view);
            mStore = itemView.findViewById(R.id.stores_image_view);
        }
    }
}
