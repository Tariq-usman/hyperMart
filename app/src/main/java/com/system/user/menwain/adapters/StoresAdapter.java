package com.system.user.menwain.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.ItemsFragment;
import com.system.user.menwain.fragments.SelectedStoreFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder> {
    private String [] productsName;
    int [] stores;
    Context context;
    public static int pos;
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
    public void onBindViewHolder(@NonNull StoresViewHolder holder, final int position) {
        holder.mProductNameView.setText(productsName[position]);
        holder.mStore.setImageResource(stores[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                Log.e("check",position+"");
                pos = position;
//                             FragmentManager fragmentManager =   ((AppCompatActivity)context).getSupportFragmentManager();
//                             fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new ItemsFragment()).addToBackStack(null).commit();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new SelectedStoreFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();


            }
        });
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
