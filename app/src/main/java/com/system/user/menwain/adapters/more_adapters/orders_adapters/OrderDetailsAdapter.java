package com.system.user.menwain.adapters.more_adapters.orders_adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.fragments.more.orders.ItemReviewFragment;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.more.orders.OrderDetailsResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsItemViewHolder> {
    private List<OrderDetailsResponse.Datum> details_list;
    Context context;
    Preferences prefrences;
    private Bundle bundle;

    public OrderDetailsAdapter(Context context, List<OrderDetailsResponse.Datum> details_list) {
        this.details_list = details_list;
        this.context = context;
        prefrences = new Preferences(context);
    }

    @NonNull
    @Override
    public OrderDetailsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_order_details, parent, false);
        OrderDetailsItemViewHolder favouriteItemViewHolder = new OrderDetailsItemViewHolder(view);
        return favouriteItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsItemViewHolder holder, final int position) {
        if (details_list.isEmpty() || details_list.size() == 0) {
            Toast.makeText(context, "No data..", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Glide.with(holder.mProduct.getContext()).load(details_list.get(position).getProductss().get(position).getImage()).into(holder.mProduct);
                holder.tvName.setText(details_list.get(position).getProductss().get(position).getName());
                holder.tvStoreName.setText(details_list.get(position).getStore().getName());
                holder.tvAmount.setText(details_list.get(position).getProductss().get(position).getPivot().getPrice().toString());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prefrences.setMoreOrdersFragStatus(3);
                    ItemReviewFragment fragment = new ItemReviewFragment();
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    bundle = new Bundle();
                    try {
                        bundle.putInt("product_id", details_list.get(position).getProductss().get(position).getProductId());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.nav_host_fragment, fragment).commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Log.e("size", String.valueOf(details_list.size()));
        return details_list.size();
    }

    public static class OrderDetailsItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mProduct;
        private TextView tvName, tvStoreName, tvAmount, tvRating;

        public OrderDetailsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mProduct = itemView.findViewById(R.id.order_details_product_view);
            tvName = itemView.findViewById(R.id.order_details_name_view);
            tvStoreName = itemView.findViewById(R.id.tv_store_name_order_details);
            tvAmount = itemView.findViewById(R.id.tv_price_order_details);
            tvRating = itemView.findViewById(R.id.tv_rating_view_order_details);
        }
    }
}
