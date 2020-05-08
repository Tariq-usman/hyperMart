package com.system.user.menwain.adapters.search;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.others.ItemDetailsFragment;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.search.SearchByBarCodeResponse;
import com.system.user.menwain.responses.search.SearchByNameResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CodeSearchAdapter extends RecyclerView.Adapter<CodeSearchAdapter.CodeViewHolder> {
    Context context;
    String imagePath;
    List<SearchByBarCodeResponse.Data.Datum> search_list;
    private CartViewModel cartViewModel;
    Bundle bundle;
    Preferences prefrences;
    int productId, intQuantity;
    String productName, storeName, price, quantity, strTotalPrice;
    float totalPrice, unitPrice;

    public CodeSearchAdapter(Context context, List<SearchByBarCodeResponse.Data.Datum> search_list) {
        this.context = context;
        this.search_list = search_list;
        prefrences = new Preferences(context);
    }

    @NonNull
    @Override
    public CodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_store_items, parent, false);
        CodeViewHolder categoryViewHolder = new CodeViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CodeViewHolder holder, final int position) {

        holder.mProductNameView.setText(search_list.get(position).getName());
        Glide.with(holder.mFilteProduct.getContext()).load(search_list.get(position).getImage()).into(holder.mFilteProduct);
        holder.mStoreName.setText(search_list.get(position).getBrand());
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
                ItemDetailsFragment fragment = new ItemDetailsFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                bundle.putString("status", "2");
                fragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, fragment).commit();
            }
        });

        holder.mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productId = search_list.get(position).getId();
                Drawable drawable = holder.mFilteProduct.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                productName = holder.mProductNameView.getText().toString();
                storeName = holder.mStoreName.getText().toString();
                price = holder.mPriceFilterItem.getText().toString();
                quantity = holder.mItemCounter.getText().toString();
                // strTotalPrice = price;
                totalPrice = Float.parseFloat(price);
                intQuantity = Integer.parseInt(quantity);
                unitPrice = totalPrice * intQuantity;
                saveToInternalStorage(bitmap);

                cartViewModel = ViewModelProviders.of((FragmentActivity) context).get(CartViewModel.class);
                Cart cart = new Cart(productId, imagePath, productName, storeName, totalPrice, unitPrice, intQuantity);
                //UpdateCartQuantity updateCartQuantity = new UpdateCartQuantity(productId, intQuantity);
                cartViewModel.insertCart(cart);
                //cartViewModel.insertAllCart(cart, updateCartQuantity);
                Toast.makeText(context, "Cart insert Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return search_list.size();
    }

    public static class CodeViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductNameView, mStoreName, mItemCounter, mPriceFilterItem;
        private CardView mAddToCart;
        private ImageView mFilteProduct, mIncreaseItems, mDecreaseItems;
        int count = 0;

        public CodeViewHolder(@NonNull View itemView) {
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
}
