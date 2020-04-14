package com.system.user.menwain.adapters.category_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.ProductDetailsResponse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectedItemsFilterAdapter extends RecyclerView.Adapter<SelectedItemsFilterAdapter.SelectedItemsFilterViewHolder> {
    private List<ProductDetailsResponse.Data.Productpic> related_items_list;
    Context context;

    public SelectedItemsFilterAdapter(Context context, List<ProductDetailsResponse.Data.Productpic> related_items_list) {
        this.related_items_list = related_items_list;
        this.context = context;
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
        holder.mProductNameView.setText(related_items_list.get(position).getImageName());
        Glide.with(holder.mProductView.getContext()).load(related_items_list.get(position).getImageUrl()).into(holder.mProductView);
       /* holder.mStoreName.setText(storeName[position]);
        holder.mProductView.setImageResource(items[position]);*/
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
        return related_items_list.size();
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
