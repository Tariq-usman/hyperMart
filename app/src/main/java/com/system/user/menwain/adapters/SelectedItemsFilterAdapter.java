package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectedItemsFilterAdapter extends RecyclerView.Adapter<SelectedItemsFilterAdapter.SelectedItemsFilterViewHolder> {
    private String[] productsName;
    private String [] storeName;
    int [] items;
    Context context;

    public SelectedItemsFilterAdapter(Context applicationContext, String[] storeName, int[] items, String[] productsName) {
        this.productsName = productsName;
        this.storeName = storeName;
        this.context = applicationContext;
        this.items = items;
    }

    @NonNull
    @Override
    public SelectedItemsFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_items_filter, parent, false);
        SelectedItemsFilterViewHolder categoryViewHolder = new SelectedItemsFilterViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemsFilterViewHolder holder, int position) {
        holder.mProductNameView.setText(productsName[position]);
        holder.mStoreName.setText(storeName[position]);
        holder.mProductView.setImageResource(items[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context, ItemDetailsFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/
//                             FragmentManager fragmentManager =   ((AppCompatActivity)context).getSupportFragmentManager();
//                             fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new ItemsFragment()).addToBackStack(null).commit();
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new ItemsFragment();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class SelectedItemsFilterViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductNameView, mStoreName;
        private ImageView mProductView;

        public SelectedItemsFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductNameView = itemView.findViewById(R.id.filter_product_name_view);
            mStoreName = itemView.findViewById(R.id.filter_product_store_name);
            mProductView = itemView.findViewById(R.id.filter_product_image_view);
        }
    }
}
