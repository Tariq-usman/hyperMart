package com.system.user.menwain.adapters.home_adapters.grid_adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.responses.home.ExploreSellAllResponse;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExploreItemsGridAdapter extends RecyclerView.Adapter<ExploreItemsGridAdapter.AllItemsGridViewHolder> {
    Context context;
    private List<ExploreSellAllResponse.Datum> explore_list;
    private CartViewModel cartViewModel;
    int productId, intQuantity;
    String imagePath,productName, storeName, price, quantity, strTotalPrice;
    float totalPrice, unitPrice;
    public ExploreItemsGridAdapter(Context applicationContext, List<ExploreSellAllResponse.Datum> explore_list) {
        this.context = applicationContext;
        this.explore_list = explore_list;
    }


    @NonNull
    @Override
    public AllItemsGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_items,parent,false);
        AllItemsGridViewHolder viewHolder = new AllItemsGridViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AllItemsGridViewHolder holder, int position) {
        Glide.with(holder.mFilteProduct.getContext()).load(explore_list.get(position).getImage()).into(holder.mFilteProduct);
        holder.mProductNameView.setText(explore_list.get(position).getName());

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

        holder.mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productId = 12;
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
                Cart cart = new Cart(productId, imagePath,productName, storeName, totalPrice, unitPrice, intQuantity);
                //UpdateCartQuantity updateCartQuantity = new UpdateCartQuantity(productId, intQuantity);
                cartViewModel.insertCart(cart);
                //cartViewModel.insertAllCart(cart, updateCartQuantity);
                Toast.makeText(context, "Cart insert Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return explore_list.size();
    }

    public class AllItemsGridViewHolder extends RecyclerView.ViewHolder{
        private TextView mProductNameView, mStoreName, mItemCounter, mPriceFilterItem;
        private CardView mAddToCart;
        private ImageView mFilteProduct, mIncreaseItems, mDecreaseItems;
        public AllItemsGridViewHolder(@NonNull View itemView) {
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
