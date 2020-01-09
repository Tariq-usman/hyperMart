package com.system.user.menwain.adapters.category_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.category.ItemsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private String[] productsName;
    private int[] items;
    public static int passId;
    Context context;
    Prefrences prefrences;
    public CategoryAdapter(Context context, String[] productsName, int[] items) {
        this.productsName = productsName;
        this.items = items;
        this.context = context;
        prefrences = new Prefrences(context);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_category, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, final int position) {
        holder.mProductNameView.setText(productsName[position]);
        holder.mProductsView.setImageResource(items[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passId = holder.getAdapterPosition();
                prefrences.setCategoryFragStatus(1);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ItemsFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView mProductNameView;
        CircleImageView mProductsView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductNameView = itemView.findViewById(R.id.product_name_view);
            mProductsView = itemView.findViewById(R.id.category_products_view);
        }
    }
}
