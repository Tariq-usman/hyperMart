package com.system.user.menwain.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.system.user.menwain.activities.ItemDetailsActivity;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.LoginActivity;
import com.system.user.menwain.entity.Cart;
import com.system.user.menwain.viewmodel.CartViewModel;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class FilterItemsAdapter extends RecyclerView.Adapter<FilterItemsAdapter.FilterItemViewHolder> {
    private String[] productsName;
    Context context;
    private int[] items;
    private String[] storesName;
    private CartViewModel cartViewModel;


    public FilterItemsAdapter(String[] productsName, Context context, int[] items, String[] storesName) {
        this.productsName = productsName;
        this.context = context;
        this.items = items;
        this.storesName = storesName;


    }

    @NonNull
    @Override
    public FilterItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_items, parent, false);
        FilterItemViewHolder categoryViewHolder = new FilterItemViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterItemViewHolder holder, int position) {
        holder.mProductNameView.setText(productsName[position]);
        holder.mFilteProduct.setImageResource(items[position]);
        holder.mStoreName.setText(storesName[position]);
        final int[] count = {1};
        holder.mIncreaseItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0] = count[0] + 1;
                holder.mItemCounter.setText("" + count[0]);
            }
        });
        holder.mDecreaseItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = holder.mItemCounter.getText().toString();
                if (Integer.valueOf(num) > 1) {
                    count[0] = count[0] - 1;
                    holder.mItemCounter.setText("" + count[0]);
                } else if (holder.mItemCounter.getText().toString().length() == 0) {
                    holder.mItemCounter.setText(0);
                }
            }
        });

        holder.mFilteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
//                             FragmentManager fragmentManager =   ((AppCompatActivity)context).getSupportFragmentManager();
//                             fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new ItemsFragment()).addToBackStack(null).commit();
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new ItemsFragment();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();


            }
        });

        holder.mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    int productId = 12;
                    String productName = holder.mProductNameView.getText().toString();
                    String storeName = holder.mStoreName.getText().toString();
                    String price = holder.mPriceFilterItem.getText().toString();
                    String quantity = holder.mItemCounter.getText().toString();
                    String strTotalPrice = price.substring(1, price.length());
                    float totalPrice = Float.parseFloat(strTotalPrice);

                    int intQuantity = Integer.parseInt(quantity);

                    float unitPrice = totalPrice * intQuantity;

                    cartViewModel = ViewModelProviders.of((FragmentActivity) context).get(CartViewModel.class);

                    Cart cart = new Cart(productId, productName, storeName, totalPrice, unitPrice, intQuantity);
                    //UpdateCartQuantity updateCartQuantity = new UpdateCartQuantity(productId, intQuantity);
                    cartViewModel.insertCart(cart);
                    //cartViewModel.insertAllCart(cart, updateCartQuantity);
                    Toast.makeText(context, "Cart insert Successfully", Toast.LENGTH_SHORT).show();
                   // Toast.makeText(context, "" + DebugDB.getAddressLog(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class FilterItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductNameView, mStoreName, mItemCounter, mPriceFilterItem;
        private CardView mAddToCart;
        private ImageView mFilteProduct, mIncreaseItems, mDecreaseItems;
        int count = 0;

        public FilterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mFilteProduct = itemView.findViewById(R.id.view_filter_product);
            mProductNameView = itemView.findViewById(R.id.filter_product_name_view);
            mIncreaseItems = itemView.findViewById(R.id.increase_items);
            mDecreaseItems = itemView.findViewById(R.id.decrees_item);
            mStoreName = itemView.findViewById(R.id.filter_store_name);
            mItemCounter = itemView.findViewById(R.id.items_counter);
            mAddToCart = itemView.findViewById(R.id.add_to_cart_filter_wrapper);
            mPriceFilterItem = itemView.findViewById(R.id.price_view_filter_item);

        }
    }
}
