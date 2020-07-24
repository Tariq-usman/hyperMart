package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.R;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.model.UpdateCartQuantity;
import com.system.user.menwain.local_db.model.UpdateCartQuantityByPid;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.SelectedItemsFilterAdapter> {

    private CartViewModel cartViewModel;
    private List<Cart> cartList = new ArrayList<>();
    private List<Float> unit_price = new ArrayList<Float>();
    private Context context;
    byte[] bytArrayImage;
    String path;
    float unitPrice;
    String subString;

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
        int id = cartList.get(position).getP_id();
        path = cart.getProduct_image();
        Bitmap myBitmap = BitmapFactory.decodeFile(path);
        holder.productView.setImageBitmap(myBitmap);
        holder.mProductNameView.setText(cart.getProduct_name());
        holder.mCartStoreName.setText(cart.getStore_name());
        holder.mCartPrice.setText("" + cart.getPer_unit_price());
        holder.mCartQuantity.setText(cart.getQuantity() + "");

        String initail_val = holder.mCartQuantity.getText().toString();
        final int[] count = {Integer.valueOf(initail_val)};
        cartViewModel.getCartDataList().observe((FragmentActivity) context, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                for (int i = 0; i < carts.size(); i++) {
                    unit_price.add(carts.get(i).getPer_unit_price());
                }
            }
        });
        holder.mIncreaseCartQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int initial_quant = Integer.parseInt(holder.mCartQuantity.getText().toString());
                count[0] = count[0] + 1;
                holder.mCartQuantity.setText("" + count[0]);
                int current_quant = count[0];
                String price = holder.mCartPrice.getText().toString();
                int index_of = price.indexOf(".");
                if (index_of != -1) {
                    subString = price.substring(0, index_of); //this will give abc
                }
                int one_item_price = Integer.parseInt(subString) / initial_quant;
                float finalPrice = Float.parseFloat(String.valueOf(one_item_price)) + Float.parseFloat(price);
                UpdateCartQuantityByPid updateCartQuantityByPid = new UpdateCartQuantityByPid(cartList.get(position).getP_id(), current_quant, finalPrice);
                cartViewModel.updateCartQuantityByPid(updateCartQuantityByPid);
            }
        });
        holder.mDecreaseCartQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int initial_quant = Integer.parseInt(holder.mCartQuantity.getText().toString());
                if (initial_quant > 1) {
                    count[0] = count[0] - 1;
                    holder.mCartQuantity.setText("" + count[0]);
                    int current_quant = count[0];
                    String price = holder.mCartPrice.getText().toString();
                    int index_of = price.indexOf(".");
                    if (index_of != -1) {
                        subString = price.substring(0, index_of); //this will give abc
                    }
                    int one_item_price = Integer.parseInt(subString) / initial_quant;
                    float finalPrice = Float.parseFloat(price) - Float.parseFloat(String.valueOf(one_item_price));
                    UpdateCartQuantityByPid updateCartQuantity = new UpdateCartQuantityByPid(cartList.get(position).getP_id(), current_quant, finalPrice);
                    cartViewModel.updateCartQuantityByPid(updateCartQuantity);
                } else if (holder.mCartQuantity.getText().toString().length() == 1) {
                    holder.mCartQuantity.setText(1 + "");
                }
            }
        });
        holder.imgDeleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartViewModel.deleteCart(getPosition(holder.getAdapterPosition()));
                Toast.makeText(context, context.getString(R.string.delete_successfully), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Cart getPosition(int position) {
        return cartList.get(position);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class SelectedItemsFilterAdapter extends RecyclerView.ViewHolder {
        TextView mProductNameView, mCartStoreName, mCartPrice, mCartQuantity;
        private ImageView productView, imgDeleteCartItem, mIncreaseCartQuantity, mDecreaseCartQuantity;

        public SelectedItemsFilterAdapter(@NonNull View itemView) {
            super(itemView);
            productView = itemView.findViewById(R.id.product_view);
            mProductNameView = itemView.findViewById(R.id.cart_product_name_view);
            mCartStoreName = itemView.findViewById(R.id.cart_product_store_name);
            mCartPrice = itemView.findViewById(R.id.cart_product_price_view);
            mCartQuantity = itemView.findViewById(R.id.cart_product_quantity_view);
            imgDeleteCartItem = itemView.findViewById(R.id.img_delete_cart_item);
            mIncreaseCartQuantity = itemView.findViewById(R.id.cart_increase_quantity);
            mDecreaseCartQuantity = itemView.findViewById(R.id.cart_decrease_quantity);
        }
    }

}
