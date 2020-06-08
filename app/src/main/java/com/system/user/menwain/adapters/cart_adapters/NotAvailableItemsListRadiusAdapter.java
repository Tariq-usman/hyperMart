package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.cart.AvailNotAvailRadiusResponse;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;

import java.util.List;

public class NotAvailableItemsListRadiusAdapter extends RecyclerView.Adapter<NotAvailableItemsListRadiusAdapter.NotAvailableItemsListViewHolder> {
    private List<AvailNotAvailRadiusResponse.Datum.Notavailable> not_avail_items_list;
    Context context;

    public NotAvailableItemsListRadiusAdapter(Context context, List<AvailNotAvailRadiusResponse.Datum.Notavailable> not_avail_items_list) {
        this.not_avail_items_list = not_avail_items_list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotAvailableItemsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_not_available_items_list, parent, false);
        NotAvailableItemsListViewHolder itemsListViewHolder = new NotAvailableItemsListViewHolder(view);
        return itemsListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotAvailableItemsListViewHolder holder, int position) {
        if (!not_avail_items_list.isEmpty()) {
            Glide.with(holder.ivProduct.getContext()).load(not_avail_items_list.get(position).getImage()).into(holder.ivProduct);
            holder.mProductNameView.setText(not_avail_items_list.get(position).getName());
            holder.mStoreName.setText(not_avail_items_list.get(position).getBrand());
            holder.mAmount.setText(not_avail_items_list.get(position).getAvgPrice().toString());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                Intent intent =new  Intent(context, ListDetailsFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        if (not_avail_items_list.size() > 0) {
            return not_avail_items_list.size();
        } else {
            return 0;
        }
    }

    public static class NotAvailableItemsListViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductNameView, mStoreName, mAmount, mAvilNotAvailItemsView;
        private ImageView mIncreaseAvailItems, mDecreaseAvailItems, ivProduct;

        public NotAvailableItemsListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.iv_product_not_avail);
            mProductNameView = itemView.findViewById(R.id.tv_product_name_not_avail);
            mStoreName = itemView.findViewById(R.id.iv_store_name_not_avail);
            mAmount = itemView.findViewById(R.id.tv_amount_not_avail);

        }
    }
}
