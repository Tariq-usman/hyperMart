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

import java.util.List;

public class ListDetailsAdapter extends RecyclerView.Adapter<ListDetailsAdapter.ListDetailsViewHolder> {
    private List<WistListByIdResopnse.Datum> products_list;
    Context context;
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
            holder.tvName.setText(products_list.get(position).getAvgPrice().toString());

    }

    @Override
    public int getItemCount() {
        return products_list.size();
    }

    public static class ListDetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView mProductImage;
        private TextView tvName,tvStoreName,tvPrice;

        public ListDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductImage = itemView.findViewById(R.id.product_image_view);
            tvName = itemView.findViewById(R.id.tv_name_my_list);
            tvStoreName = itemView.findViewById(R.id.tv_store_name_my_list);
            tvPrice = itemView.findViewById(R.id.tv_price_my_list);
        }
    }
}
