package com.system.user.menwain.adapters.cart_adapters.apply_filter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.cart.apply_filter.DialogFragmentFilterStores;
import com.system.user.menwain.fragments.more.stores.SelectedStoreFragment;
import com.system.user.menwain.fragments.more.stores.StoresFragment;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.more.stores.StoresAllBranchesResponse;

import java.util.List;

public class FilterStoresAdapter extends RecyclerView.Adapter<FilterStoresAdapter.StoresViewHolder> {
    List<StoresAllBranchesResponse.Storelist.Datum> stores_list;
    static Context context;
    private Preferences prefrences;
    private Bundle bundle;
    private static int adapter_position = -1;
    int pos;

    public FilterStoresAdapter(Context context, List<StoresAllBranchesResponse.Storelist.Datum> stores_list) {
        this.context = context;
        this.stores_list = stores_list;
        prefrences = new Preferences(context);
    }

    @NonNull
    @Override
    public StoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_filter_stores, parent, false);
        StoresViewHolder categoryViewHolder = new StoresViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StoresViewHolder holder, final int position) {
        holder.mStoreNameView.setText(stores_list.get(position).getName());
        holder.ratingBar.setRating(stores_list.get(position).getAverageRating());
        holder.mRatingView.setText("( " + stores_list.get(position).getAverageRating() + " )");
        Glide.with(holder.mStore.getContext()).load(stores_list.get(position).getImage()).into(holder.mStore);

        if (DialogFragmentFilterStores.check_value == true) {
            SelectedStoreFragment.store_id_list.add(stores_list.get(position).getId());
        } else {
            SelectedStoreFragment.store_id_list.clear();
        }

        holder.checkBox.setChecked(DialogFragmentFilterStores.check_value);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pos = holder.getAdapterPosition();
                if (!isChecked) {
                    holder.checkBox.setChecked(false);
                    if (SelectedStoreFragment.store_id_list.size() > 0) {
                        for (int i = 0; i < SelectedStoreFragment.store_id_list.size(); i++) {
                            if (SelectedStoreFragment.store_id_list.get(i) == stores_list.get(position).getId()) {
                                SelectedStoreFragment.store_id_list.remove(i);
                            }
                        }
                    }
                } else {
                    holder.checkBox.setChecked(true);
                    if (SelectedStoreFragment.store_id_list.size() == 0) {
                        SelectedStoreFragment.store_id_list.add(stores_list.get(position).getId());
                    } else {
                        SelectedStoreFragment.store_id_list.add(stores_list.get(pos).getId());
                    }

                }
                Log.d("add_id", String.valueOf(SelectedStoreFragment.store_id_list));
            }
        });



    }

    @Override
    public int getItemCount() {
        return stores_list.size();
    }

    public class StoresViewHolder extends RecyclerView.ViewHolder {
        TextView mStoreNameView, mRatingView;
        ImageView mStore;
        private RatingBar ratingBar;
        private CheckBox checkBox;

        public StoresViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_filter_stores);
            ratingBar = itemView.findViewById(R.id.rating_bar_apply_filter_stores);
            mStoreNameView = itemView.findViewById(R.id.apply_filter_store_name_view);
            mStore = itemView.findViewById(R.id.apply_filter_stores_image_view);
            mRatingView = itemView.findViewById(R.id.ratting_view_apply_filter_store);
        }
    }
}
