package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.R;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.model.UpdateCartQuantity;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;

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
    public void onBindViewHolder(@NonNull final SelectedItemsFilterAdapter holder, final int position) {
        Cart cart = cartList.get(position);
        holder.mProductNameView.setText(cart.getProduct_name());
        holder.mCartStoreName.setText(cart.getStore_name());
        holder.mCartPrice.setText("$ "+cart.getPer_unit_price());
        holder.mCartQuantity.setText(cart.getQuantity()+"");

        String initail_val =holder.mCartQuantity.getText().toString();
        final int[] count = {Integer.valueOf(initail_val)};

        holder.mIncreaseCartQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0] = count[0] + 1;
                holder.mCartQuantity.setText(""+ count[0]);
                int quant = count[0];

                String price = holder.mCartPrice.getText().toString();
                String strTotalPrice = price.substring(1,price.length());
                float fTotalPrice = Float.parseFloat(strTotalPrice);
                float unitPrice = 150;

                float finalPrice = unitPrice + fTotalPrice;



                UpdateCartQuantity updateCartQuantity = new UpdateCartQuantity(cartList.get(position).getId(), quant, finalPrice);
                cartViewModel.updateCartQuantity(updateCartQuantity);
            }
        });
        holder.mDecreaseCartQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num =holder.mCartQuantity.getText().toString();
                if (Integer.valueOf(num) > 1) {
                    count[0] = count[0] - 1;
                    holder.mCartQuantity.setText("" + count[0]);
                    int quant = count[0];

                    String price = holder.mCartPrice.getText().toString();
                    String strTotalPrice = price.substring(1,price.length());
                    float fTotalPrice = Float.parseFloat(strTotalPrice);
                    float unitPrice = 150;

                    float finalPrice = fTotalPrice - unitPrice ;

                    UpdateCartQuantity updateCartQuantity = new UpdateCartQuantity(cartList.get(position).getId(), quant, finalPrice);
                    cartViewModel.updateCartQuantity(updateCartQuantity);
                }else if (holder.mCartQuantity.getText().toString().length()==1){
                    holder.mCartQuantity.setText(1+"");
                }
            }
        });
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
        private ImageView imgDeleteCartItem,mIncreaseCartQuantity,mDecreaseCartQuantity;

        public SelectedItemsFilterAdapter(@NonNull View itemView) {
            super(itemView);
            mProductNameView = itemView.findViewById(R.id.cart_product_name_view);
            mCartStoreName= itemView.findViewById(R.id.cart_product_store_name);
            mCartPrice = itemView.findViewById(R.id.cart_product_price_view);
            mCartQuantity= itemView.findViewById(R.id.cart_product_quantity_view);
            imgDeleteCartItem = itemView.findViewById(R.id.img_delete_cart_item);
            mIncreaseCartQuantity = itemView.findViewById(R.id.cart_increase_quantity);
            mDecreaseCartQuantity = itemView.findViewById(R.id.cart_decrease_quantity);
        }
    }
}
