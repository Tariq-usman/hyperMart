package com.system.user.menwain.adapters.my_lists_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.my_list.WistListByIdResopnse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListDetailsAdapter extends RecyclerView.Adapter<ListDetailsAdapter.ListDetailsViewHolder> {
    private List<WistListByIdResopnse.Datum> products_list;
    Context context;
    public static List<Integer> quantity_list = new ArrayList<>();
    public static List<Integer> amount_list = new ArrayList<>();
    public ListDetailsAdapter(Context applicationContext, List<WistListByIdResopnse.Datum> products_list) {
        this.products_list = products_list;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public ListDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_details, parent, false);
        ListDetailsViewHolder listDetailsViewHolder = new ListDetailsViewHolder(view);
        return listDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListDetailsViewHolder holder, int position) {
            Glide.with(holder.mProductImage.getContext()).load(products_list.get(position).getImage()).into(holder.mProductImage);
            holder.tvName.setText(products_list.get(position).getName());
            holder.tvStoreName.setText(products_list.get(position).getBrand());
            holder.tvPrice.setText(products_list.get(position).getAvgPrice().toString());
        for (int i = 0; i < products_list.size(); i++) {
            quantity_list.add(Integer.valueOf(holder.tvQuantity.getText().toString()));
            amount_list.add(Integer.valueOf(holder.tvPrice.getText().toString()));
        }
    }

    @Override
    public int getItemCount() {
        return products_list.size();
    }

    public static class ListDetailsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mProductImage,mIncrease,mDecrease;
        private TextView tvName,tvStoreName,tvPrice,tvQuantity;

        public ListDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductImage = itemView.findViewById(R.id.product_image_view);
            mDecrease = itemView.findViewById(R.id.decrees_item_my_list);
            mIncrease = itemView.findViewById(R.id.increase_items_my_list);
            tvName = itemView.findViewById(R.id.tv_name_my_list);
            tvStoreName = itemView.findViewById(R.id.tv_store_name_my_list);
            tvPrice = itemView.findViewById(R.id.tv_price_my_list);
            tvQuantity = itemView.findViewById(R.id.items_counter_my_list);
        }
    }
}
