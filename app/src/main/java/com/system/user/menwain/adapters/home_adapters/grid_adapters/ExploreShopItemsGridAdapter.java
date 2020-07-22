package com.system.user.menwain.adapters.home_adapters.grid_adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.others.ItemDetailsFragment;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.model.UpdateCartQuantity;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.home.ExploreShopeSeeAllResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExploreShopItemsGridAdapter extends RecyclerView.Adapter<ExploreShopItemsGridAdapter.AllItemsGridViewHolder> {
    private Context context;
    private List<ExploreShopeSeeAllResponse.Datum> explore_shop_grid_list;
    private CartViewModel cartViewModel;
    private int productId, intQuantity;
    private String imagePath, productName, storeName, price, quantity, strTotalPrice;
    private float totalPrice, unitPrice;
    private UpdateCartQuantity updateCartQuantity;
    private int id, pro_quantity;
    private List<Integer> p_id_list = new ArrayList<Integer>();
    private List<Integer> quantity_list = new ArrayList<Integer>();
    private Bitmap bitmap;
    private Bundle bundle;
    private Preferences prefrences;

    public ExploreShopItemsGridAdapter(Context context, List<ExploreShopeSeeAllResponse.Datum> explore_shop_grid_list) {
        this.context = context;
        this.explore_shop_grid_list = explore_shop_grid_list;
        prefrences = new Preferences(context);
    }

    @NonNull
    @Override
    public AllItemsGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_items, parent, false);
        AllItemsGridViewHolder viewHolder = new AllItemsGridViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AllItemsGridViewHolder holder, final int position) {
        Glide.with(holder.mFilteProduct.getContext()).load(explore_shop_grid_list.get(position).getImage()).into(holder.mFilteProduct);
        holder.mProductNameView.setText(explore_shop_grid_list.get(position).getName());

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
                try {
                    productId = explore_shop_grid_list.get(position).getId();
                    Drawable drawable = holder.mFilteProduct.getDrawable();
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    productName = holder.mProductNameView.getText().toString();
                    price = holder.mPriceFilterItem.getText().toString();
                    quantity = holder.mItemCounter.getText().toString();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setHomeFragStatus(4);
                bundle = new Bundle();
                ItemDetailsFragment fragment = new ItemDetailsFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                bundle.putString("status", "1");
                bundle.putInt("product_id", explore_shop_grid_list.get(position).getId());
                fragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, fragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return explore_shop_grid_list.size();
    }

    public class AllItemsGridViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductNameView, mStoreName, mItemCounter, mPriceFilterItem;
        private CardView mAddToCart;
        private ImageView mFilteProduct, mIncreaseItems, mDecreaseItems;

        public AllItemsGridViewHolder(@NonNull View itemView) {
            super(itemView);
            mFilteProduct = itemView.findViewById(R.id.view_filter_product);
            mProductNameView = itemView.findViewById(R.id.filter_product_name_view);
            mIncreaseItems = itemView.findViewById(R.id.increase_items);
            mDecreaseItems = itemView.findViewById(R.id.decrees_item);
//            mStoreName = itemView.findViewById(R.id.filter_store_name);
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
