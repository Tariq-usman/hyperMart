package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.R;
import com.system.user.menwain.entity.Cart;
import com.system.user.menwain.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.SelectedItemsFilterAdapter> {

    private CartViewModel cartViewModel;
    private List<Cart> cartList = new ArrayList<>();
    private Context context;

    public void setCartData(List<Cart> cartList, CartViewModel cartViewModel) {
        this.cartList = cartList;
        this.cartViewModel = cartViewModel;
    }

    @NonNull
    @Override
    public SelectedItemsFilterAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_cart, parent, false);
        SelectedItemsFilterAdapter categoryViewHolder = new SelectedItemsFilterAdapter(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectedItemsFilterAdapter holder, int position) {
        Cart cart = cartList.get(position);
        holder.mProductNameView.setText(cart.getProduct_name());
        holder.mCartStoreName.setText(cart.getStore_name());
        holder.mCartPrice.setText("$ "+cart.getPer_unit_price()+"");
        holder.mCartQuantity.setText(cart.getQuantity()+"");

        holder.imgDeleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cartViewModel.deleteCart(getPosition(holder.getAdapterPosition()));
                Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Cart getPosition(int position)
    {
        return cartList.get(position);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class SelectedItemsFilterAdapter extends RecyclerView.ViewHolder {
        TextView mProductNameView,mCartStoreName,mCartPrice,mCartQuantity;
        private ImageView imgDeleteCartItem;

        public SelectedItemsFilterAdapter(@NonNull View itemView) {
            super(itemView);
            mProductNameView = itemView.findViewById(R.id.cart_product_name_view);
            mCartStoreName= itemView.findViewById(R.id.cart_product_store_name);
            mCartPrice = itemView.findViewById(R.id.cart_product_price_view);
            mCartQuantity= itemView.findViewById(R.id.cart_product_quantity_view);
            imgDeleteCartItem = itemView.findViewById(R.id.img_delete_cart_item);
        }
    }
}
