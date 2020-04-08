package com.system.user.menwain.adapters.more_adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.others.ItemDetailsFragment;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.responses.more.stores.SelectedStoreResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SelectedStoresCategoryProductsAdapter extends RecyclerView.Adapter<SelectedStoresCategoryProductsAdapter.FilterStoresViewHolder> {

    private List<SelectedStoreResponse.Product.Datum> category_products_list;
    Context context;
    byte[] productImage;
    String productName, storeName, price, quantity, strTotalPrice, imagePath;
    private CartViewModel cartViewModel;
    Bundle bundle;
    private Prefrences prefrences;

    public SelectedStoresCategoryProductsAdapter(Context context, List<SelectedStoreResponse.Product.Datum> category_products_list) {
        this.category_products_list = category_products_list;
        this.context = context;
        prefrences = new Prefrences(context);

    }

    @NonNull
    @Override
    public FilterStoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_items, parent, false);
        FilterStoresViewHolder categoryViewHolder = new FilterStoresViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterStoresViewHolder holder, final int position) {
        Glide.with(holder.mFilteProduct.getContext()).load(category_products_list.get(position).getProducts().get(position).getImage()).into(holder.mFilteProduct);
        holder.mProductNameView.setText(category_products_list.get(position).getProducts().get(position).getName());
        holder.mStoreName.setText(category_products_list.get(position).getName());
        holder.mPriceFilterItem.setText(category_products_list.get(position).getProducts().get(position).getAvgPrice().toString());
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
                bundle = new Bundle();
                prefrences.setMoreStoresFragStatus(3);
                ItemDetailsFragment fragment = new ItemDetailsFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                bundle.putString("status", "3");
               /* if (position == items.length - 1) {
                    bundle.putString("image_url", String.valueOf(items[position]));
                    bundle.putString("image_url1", String.valueOf(items[position - 1]));
                    bundle.putString("image_url2", String.valueOf(items[position - 2]));
                } else if (position==items.length-2){
                    bundle.putString("image_url", String.valueOf(items[position]));
                    bundle.putString("image_url1", String.valueOf(items[position + 1]));
                    bundle.putString("image_url2", String.valueOf(items[position - 2]));
                }else if (position==items.length-3){
                    bundle.putString("image_url", String.valueOf(items[position]));
                    bundle.putString("image_url1", String.valueOf(items[position + 1]));
                    bundle.putString("image_url2", String.valueOf(items[position + 2]));
                }else {
                    bundle.putString("image_url", String.valueOf(items[position]));
                    bundle.putString("image_url1", String.valueOf(items[position + 1]));
                    bundle.putString("image_url2", String.valueOf(items[position + 2]));
                }*/
                fragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, fragment).commit();
            }
        });

        holder.mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable drawable = holder.mFilteProduct.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                int productId = 12;
                productName = holder.mProductNameView.getText().toString();
                storeName = holder.mStoreName.getText().toString();
                price = holder.mPriceFilterItem.getText().toString();
                quantity = holder.mItemCounter.getText().toString();
                strTotalPrice = price;
                float totalPrice = Float.parseFloat(strTotalPrice);

                int intQuantity = Integer.parseInt(quantity);

                float unitPrice = totalPrice * intQuantity;
                saveToInternalStorage(bitmap);

                cartViewModel = ViewModelProviders.of((FragmentActivity) context).get(CartViewModel.class);

                Cart cart = new Cart(productId, imagePath, productName, storeName, totalPrice, unitPrice, intQuantity);
                //UpdateCartQuantity updateCartQuantity = new UpdateCartQuantity(productId, intQuantity);
                cartViewModel.insertCart(cart);
                //cartViewModel.insertAllCart(cart, updateCartQuantity);
                Toast.makeText(context, "Cart insert Successfully", Toast.LENGTH_SHORT).show();
                // Toast.makeText(context, "" + DebugDB.getAddressLog(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, productName + ".jpg");
        imagePath = String.valueOf(mypath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    @Override
    public int getItemCount() {
        return category_products_list.size();
    }

    public static class FilterStoresViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductNameView, mStoreName, mItemCounter, mPriceFilterItem;
        private CardView mAddToCart;
        private ImageView mFilteProduct, mIncreaseItems, mDecreaseItems;
        int count = 0;

        public FilterStoresViewHolder(@NonNull View itemView) {
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