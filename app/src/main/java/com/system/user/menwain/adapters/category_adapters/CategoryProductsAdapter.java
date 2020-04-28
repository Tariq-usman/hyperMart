package com.system.user.menwain.adapters.category_adapters;

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
import com.system.user.menwain.local_db.model.UpdateCartQuantity;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.fragments.others.ItemDetailsFragment;
import com.system.user.menwain.R;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.responses.category.CategoryResponse;

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
import java.util.ArrayList;
import java.util.List;

public class CategoryProductsAdapter extends RecyclerView.Adapter<CategoryProductsAdapter.FilterItemViewHolder> {
    Context context;
    String imagePath;
    List<CategoryResponse.Products.Datum_> subCatergoryList;
    private CartViewModel cartViewModel;
    Bundle bundle;
    Preferences prefrences;
    int productId, intQuantity;
    String productName, storeName, price, quantity, strTotalPrice;
    float totalPrice, unitPrice;
    private List<Integer> p_id_list = new ArrayList<Integer>();
    List<Integer> quantity_list = new ArrayList<Integer>();

    UpdateCartQuantity updateCartQuantity;
    int id, pro_quantity;
    FileOutputStream fos = null;

    public CategoryProductsAdapter(Context context, List<CategoryResponse.Products.Datum_> subCatergoryList) {
        this.context = context;
        this.subCatergoryList = subCatergoryList;
        prefrences = new Preferences(context);
    }

    @NonNull
    @Override
    public FilterItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_items, parent, false);
        FilterItemViewHolder categoryViewHolder = new FilterItemViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterItemViewHolder holder, final int position) {

        Glide.with(holder.mFilteProduct.getContext()).load(subCatergoryList.get(position).getImage()).into(holder.mFilteProduct);
        holder.mProductNameView.setText(subCatergoryList.get(position).getDescription());
        //  holder.mStoreName.setText(subCatergoryList.get(position).getBrand());
        holder.mPriceFilterItem.setText(subCatergoryList.get(position).getLowestPrice().toString());
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
                prefrences.setCategoryFragStatus(2);
                ItemDetailsFragment fragment = new ItemDetailsFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                bundle.putString("status", "2");
                bundle.putInt("product_id", subCatergoryList.get(position).getId());
                fragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, fragment).commit();
            }
        });

        holder.mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productId = subCatergoryList.get(position).getId();
                Drawable drawable = holder.mFilteProduct.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                productName = subCatergoryList.get(position).getName();
                //  storeName = holder.mStoreName.getText().toString();
                price = subCatergoryList.get(position).getLowestPrice().toString();
                quantity = holder.mItemCounter.getText().toString();
                // strTotalPrice = price;
                totalPrice = Float.parseFloat(price);
                intQuantity = Integer.parseInt(quantity);
                unitPrice = totalPrice * intQuantity;
                saveToInternalStorage(bitmap);

                cartViewModel = ViewModelProviders.of((FragmentActivity) context).get(CartViewModel.class);
                final Cart cart = new Cart(productId, imagePath, productName, storeName, totalPrice, unitPrice, intQuantity);
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
                    Toast.makeText(context, "Cart insert Successfully", Toast.LENGTH_SHORT).show();
                } else {

                    for (int i = 0; i < p_id_list.size(); i++) {
                        if (p_id_list.get(i) == productId) {
                            id = p_id_list.get(i);
                            pro_quantity= quantity_list.get(i);
                        }
                    }

                    if (id == productId) {
                        int final_quantity = intQuantity+pro_quantity;
                        updateCartQuantity = new UpdateCartQuantity(productId, intQuantity, unitPrice);
                        cartViewModel.updateCartQuantity(updateCartQuantity);
                        Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        cartViewModel.insertCart(cart);
                        Toast.makeText(context, "Cart insert Successfully", Toast.LENGTH_SHORT).show();
                    }
                }

//                UpdateCartQuantity updateCartQuantity = new UpdateCartQuantity(productId, intQuantity,unitPrice);
//                cartViewModel.insertAllCart(cart, updateCartQuantity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCatergoryList.size();
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
            // mStoreName = itemView.findViewById(R.id.filter_store_name);
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
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
            //fos.close();
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
