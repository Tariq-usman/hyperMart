package com.system.user.menwain.adapters.more_adapters.orders_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.responses.more.orders.PrecessingOrdersResponse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrdersProcessingAdapter extends RecyclerView.Adapter<OrdersProcessingAdapter.OrdersItemViewHolder>{
    private List<PrecessingOrdersResponse.Allorders.Datum> processing_orders_list;
    Context context;

    public OrdersProcessingAdapter(Context context, List<PrecessingOrdersResponse.Allorders.Datum> processing_orders_list) {
        this.context = context;
        this.processing_orders_list = processing_orders_list;
    }

    @NonNull
    @Override
    public OrdersItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_orders,parent,false);
        OrdersItemViewHolder favouriteItemViewHolder = new OrdersItemViewHolder(view);
        return favouriteItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersItemViewHolder holder, int position) {
        holder.tvOrderNo.setText(processing_orders_list.get(position).getId().toString());
        holder.tvOrderStatus.setText(processing_orders_list.get(position).getOrderStatus());
        String date_time = processing_orders_list.get(position).getDateTime();
        String[] split_date_time = date_time.split(" ");
        String date = split_date_time[0];
        holder.tvOrderDate.setText(date);
        holder.tvTotalPrice.setText(processing_orders_list.get(position).getTotalPrice() +" SAR");
      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return processing_orders_list.size();
    }

    public static class OrdersItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTotalPrice,tvOrderNo,tvOrderStatus,tvOrderDate;
        public OrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNo = itemView.findViewById(R.id.tv_order_no_all_orders);
            tvOrderStatus = itemView.findViewById(R.id.tv_ordr_status_all_orders);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date_all_orders);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_amount_all_orders);
        }
    }
}
