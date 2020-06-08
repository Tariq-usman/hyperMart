package com.system.user.menwain.adapters.more_adapters.orders_adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.system.user.menwain.fragments.more.orders.OrderDetailsFragment;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.fragments.more.orders.ItemReviewFragment;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.more.orders.OrderDetailsResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsItemViewHolder> {
    private List<OrderDetailsResponse.Data.Products> details_list;
    private Context context;
    private Preferences prefrences;
    private Bundle bundle;
    private String order_status;
    public static List<Integer> orders_quantity_list = new ArrayList<>();
    public static List<Integer> orders_amount_list = new ArrayList<>();
    public OrderDetailsAdapter(Context context, List<OrderDetailsResponse.Data.Products> details_list, String order_status) {
        this.details_list = details_list;
        this.context = context;
        prefrences = new Preferences(context);
        this.order_status = order_status;
    }

    @NonNull
    @Override
    public OrderDetailsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_order_details, parent, false);
        OrderDetailsItemViewHolder favouriteItemViewHolder = new OrderDetailsItemViewHolder(view);
        return favouriteItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderDetailsItemViewHolder holder, final int position) {
        if (details_list.isEmpty() || details_list.size() == 0) {
            Toast.makeText(context, "No data..", Toast.LENGTH_SHORT).show();
        } else {
            try {

                Glide.with(holder.mProduct.getContext()).load(details_list.get(position).getImage()).into(holder.mProduct);
                holder.tvName.setText(details_list.get(position).getName());
                holder.tvStoreName.setText(OrderDetailsFragment.store_name);
                holder.tvAmount.setText(details_list.get(position).getPivot().getPrice().toString());
//                holder.tvRating.setText(details_list.get(position).get);
                if (order_status.equalsIgnoreCase("delivered")){
                    holder.tvReviews.setVisibility(View.VISIBLE);
                }else {
                    holder.tvReviews.setVisibility(View.GONE);
                }
                for (int i = 0; i < details_list.size(); i++) {
                    orders_quantity_list.add(Integer.valueOf(details_list.get(position).getPivot().getQuantity()));
                    orders_amount_list.add(Integer.valueOf(details_list.get(position).getStoreprice()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.tvReviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prefrences.setMoreOrdersFragStatus(3);
                    ItemReviewFragment fragment = new ItemReviewFragment();
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    bundle = new Bundle();
                    try {
                        bundle.putInt("product_id", details_list.get(position).getProductId());
                        bundle.putString("image", details_list.get(position).getImage());
                        bundle.putString("product_name", details_list.get(position).getName());
                        bundle.putString("price", details_list.get(position).getPivot().getPrice().toString());
                        bundle.putString("description", details_list.get(position).getBrand());
//                        bundle.putString("date", details_list.get(position).getDateTime());
                    } catch (Exception e) {
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
        private ImageView mProduct;
        private TextView tvReviews, tvName, tvStoreName, tvAmount, tvRating;

        public OrderDetailsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mProduct = itemView.findViewById(R.id.order_details_product_view);
            tvName = itemView.findViewById(R.id.order_details_name_view);
            tvStoreName = itemView.findViewById(R.id.tv_store_name_order_details);
            tvAmount = itemView.findViewById(R.id.tv_price_order_details);
            tvRating = itemView.findViewById(R.id.tv_rating_view_order_details);
            tvReviews = itemView.findViewById(R.id.tv_review);
        }
    }
}
