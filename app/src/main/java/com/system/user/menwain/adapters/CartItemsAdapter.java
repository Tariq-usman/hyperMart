package com.system.user.menwain.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.SelectedItemsFilterAdapter> {
    private String[] productsName;

    public CartItemsAdapter(String[] productsName) {
        this.productsName = productsName;
    }

    @NonNull
    @Override
    public SelectedItemsFilterAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_cart, parent, false);
        SelectedItemsFilterAdapter categoryViewHolder = new SelectedItemsFilterAdapter(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemsFilterAdapter holder, int position) {
        holder.mProductNameView.setText(productsName[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context, ItemDetailsActivity.class);
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

    public static class SelectedItemsFilterAdapter extends RecyclerView.ViewHolder {
        TextView mProductNameView;

        public SelectedItemsFilterAdapter(@NonNull View itemView) {
            super(itemView);
            mProductNameView = itemView.findViewById(R.id.cart_product_name_view);
        }
    }
}
