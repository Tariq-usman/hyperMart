package com.system.user.menwain.adapters.category_adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.category.CategoryItemsFragment;
import com.system.user.menwain.responses.category.SuperCategoryResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<SuperCategoryResponse.SuperCategory.Datum> categoryList;
    public static int passId;
    Context context;
    Prefrences prefrences;
    Bundle bundle;

    public CategoryAdapter(Context context, List<SuperCategoryResponse.SuperCategory.Datum> categoryList) {
        this.categoryList = categoryList;
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
        Glide.with(holder.mProductsView.getContext()).load(categoryList.get(position).getPicture()).into(holder.mProductsView);
        holder.mProductNameView.setText(categoryList.get(position).getDescription());
        holder.mProductPrice.setText(categoryList.get(position).getLowestPrice() + "-" + categoryList.get(position).getHighestPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passId = holder.getAdapterPosition();

                bundle = new Bundle();
                CategoryItemsFragment fragment = new CategoryItemsFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();

                bundle.putString("id", categoryList.get(position).getId().toString());
                prefrences.setCategoryFragStatus(1);
                fragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, fragment).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductNameView, mProductPrice;
        CircleImageView mProductsView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductNameView = itemView.findViewById(R.id.product_name_view);
            mProductPrice = itemView.findViewById(R.id.category_price);
            mProductsView = itemView.findViewById(R.id.category_products_view);
        }
    }
}
