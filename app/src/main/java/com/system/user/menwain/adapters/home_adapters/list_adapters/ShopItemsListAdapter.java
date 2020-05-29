package com.system.user.menwain.adapters.home_adapters.list_adapters;

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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.model.UpdateCartQuantity;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.responses.home.ShopSeeAllResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShopItemsListAdapter extends RecyclerView.Adapter<ShopItemsListAdapter.ItemsListViewHolder> {
    Context context;
    private List<ShopSeeAllResponse.Datum> shop_list;
    int productId, intQuantity;
    String imagePath, productName, storeName, price, quantity, strTotalPrice;
    float totalPrice, unitPrice;
    private CartViewModel cartViewModel;
    UpdateCartQuantity updateCartQuantity;
    int id, pro_quantity;
    private List<Integer> p_id_list = new ArrayList<Integer>();
    List<Integer> quantity_list = new ArrayList<Integer>();

    public ShopItemsListAdapter(Context context, List<ShopSeeAllResponse.Datum> shop_list) {
        this.context = context;
        this.shop_list = shop_list;
    }

    @NonNull
    @Override
    public ItemsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_all_items_list, parent, false);
        ItemsListViewHolder viewHolder = new ItemsListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemsListViewHolder holder, final int position) {
        Glide.with(holder.ivAllItemsList.getContext()).load(shop_list.get(position).getImage()).into(holder.ivAllItemsList);
        holder.tvAllItemsList.setText(shop_list.get(position).getName());

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
                try{
                productId = shop_list.get(position).getId();
                Drawable drawable = holder.ivAllItemsList.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                productName = holder.tvAllItemsList.getText().toString();
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
                cartViewModel.getCartDataList().observe((FragmentActivity) context, new Observer<List<Cart>>() {
                    @Override
                    public void onChanged(List<Cart> carts) {

                        for (int i = 0; i < carts.size(); i++) {
                            p_id_list.add(carts.get(i).getP_id());
                            quantity_list.add(carts.get(i).getQuantity());
                        }
                    }
                });
                if (p_id_list.size() == 0) {
                    cartViewModel.insertCart(cart);
                    Toast.makeText(context, context.getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
                } else {

                    for (int i = 0; i < p_id_list.size(); i++) {
                        if (p_id_list.get(i) == productId) {
                            id = p_id_list.get(i);
                            pro_quantity = quantity_list.get(i);
                        }
                    }

                    if (id == productId) {
                        int final_quantity = intQuantity + pro_quantity;
                        updateCartQuantity = new UpdateCartQuantity(productId, intQuantity, unitPrice);
                        cartViewModel.updateCartQuantity(updateCartQuantity);
                        Toast.makeText(context, context.getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                    } else {
                        cartViewModel.insertCart(cart);
                        Toast.makeText(context, context.getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch(
            Exception e)

            {
                e.printStackTrace();
            }
        }

    });
}

    @Override
    public int getItemCount() {
        return shop_list.size();
    }

public class ItemsListViewHolder extends RecyclerView.ViewHolder {
    private TextView tvAllItemsList, mStoreName, mItemCounter, mPriceFilterItem;
    private CardView mAddToCart;
    private ImageView ivAllItemsList, mIncreaseItems, mDecreaseItems;

    public ItemsListViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAllItemsList = itemView.findViewById(R.id.tv_title_list_view);
        ivAllItemsList = itemView.findViewById(R.id.image_view_product_list_view);
        mIncreaseItems = itemView.findViewById(R.id.increase_items_list_view);
        mDecreaseItems = itemView.findViewById(R.id.decrees_item_list_view);
        mStoreName = itemView.findViewById(R.id.tv_store_name_list_view);
        mItemCounter = itemView.findViewById(R.id.tv_items_counter_list_view);
        mAddToCart = itemView.findViewById(R.id.add_to_cart_list_view);
        mPriceFilterItem = itemView.findViewById(R.id.tv_price_list_view);
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
