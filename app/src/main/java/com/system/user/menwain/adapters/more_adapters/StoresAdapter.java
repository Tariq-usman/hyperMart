package com.system.user.menwain.adapters.more_adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.more.stores.SelectedStoreFragment;
import com.system.user.menwain.responses.more.stores.StoresAllBranchesResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder> {
    List<StoresAllBranchesResponse.Storelist> stores_list;
    static Context context;
    public static int pos;
    private Prefrences prefrences;
    private Bundle bundle;

    public StoresAdapter(Context context, List<StoresAllBranchesResponse.Storelist> stores_list) {
        this.stores_list = stores_list;
        this.context = context;
        prefrences = new Prefrences(context);
    }

    @NonNull
    @Override
    public StoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_stores, parent, false);
        StoresViewHolder categoryViewHolder = new StoresViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StoresViewHolder holder, final int position) {
        holder.mProductNameView.setText(stores_list.get(position).getName());
        holder.ratingBar.setRating(stores_list.get(position).getAverageRating());
        holder.mRatingView.setText("( " + stores_list.get(position).getAverageRating() + " )");
        Glide.with(holder.mStore.getContext()).load(stores_list.get(position).getImage()).into(holder.mStore);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = holder.getAdapterPosition();
                prefrences.setMoreStoresFragStatus(2);

                bundle = new Bundle();
                Fragment myFragment = new SelectedStoreFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                bundle.putInt("store_id", stores_list.get(position).getId());
                myFragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, myFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores_list.size();
    }

    public static class StoresViewHolder extends RecyclerView.ViewHolder {
        TextView mProductNameView, mRatingView;
        ImageView mStore;
        private RatingBar ratingBar;

        public StoresViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.rating_bar_stores);
            mProductNameView = itemView.findViewById(R.id.store_name_view);
            mStore = itemView.findViewById(R.id.stores_image_view);
            mRatingView = itemView.findViewById(R.id.ratting_view);
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            //stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.colorButton), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.yellowColor), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellowColor), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
