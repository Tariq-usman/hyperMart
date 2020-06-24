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
        path = cart.getProduct_image();
        Bitmap myBitmap = BitmapFactory.decodeFile(path);
        holder.productView.setImageBitmap(myBitmap);
        holder.mProductNameView.setText(cart.getProduct_name());
        holder.mCartStoreName.setText(cart.getStore_name());
        holder.mCartPrice.setText(""+cart.getPer_unit_price());
        holder.mCartQuantity.setText(cart.getQuantity()+"");

        String initail_val =holder.mCartQuantity.getText().toString();
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
                count[0] = count[0] + 1;
                holder.mCartQuantity.setText(""+ count[0]);
                int quant = count[0];
                String price = holder.mCartPrice.getText().toString();
                String strTotalPrice = price.substring(1,price.length());
                float fTotalPrice = Float.parseFloat(strTotalPrice);
                for (int i=0;i<unit_price.size();i++){
                     unitPrice = unit_price.get(position);
                }
//                float unitPrice = Float.parseFloat(holder.mCartPrice.getText().toString().substring(1));
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
                    for (int i=0;i<unit_price.size();i++){
                        unitPrice = unit_price.get(position);
                    }
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
                Toast.makeText(context, context.getString(R.string.delete_successfully), Toast.LENGTH_SHORT).show();
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
        private ImageView productView,imgDeleteCartItem,mIncreaseCartQuantity,mDecreaseCartQuantity;

        public SelectedItemsFilterAdapter(@NonNull View itemView) {
            super(itemView);
            productView = itemView.findViewById(R.id.product_view);
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
