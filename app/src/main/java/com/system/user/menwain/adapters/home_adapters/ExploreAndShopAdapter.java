package com.system.user.menwain.adapters.home_adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.home.HomeExploreAndShop;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExploreAndShopAdapter extends RecyclerView.Adapter<ExploreAndShopAdapter.ViewHolder> {
    Context context;
    List<HomeExploreAndShop.Dymmy1> exploreShopList;
    public boolean status = false;
    Bundle bundle;
    Prefrences prefrences;

    public ExploreAndShopAdapter(Context context, List<HomeExploreAndShop.Dymmy1> exploreShopList) {
        this.context = context;
        this.exploreShopList = exploreShopList;
        prefrences = new Prefrences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_explore_shop, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(holder.mExploreShopImage.getContext()).load(exploreShopList.get(position).getImage()).into(holder.mExploreShopImage);
        holder.mExploreShopStatus.setText(exploreShopList.get(position).getName());
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setHomeFragStatus(4);
                bundle = new Bundle();
                ItemDetailsFragment fragment = new ItemDetailsFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                bundle.putString("status", "1");
               *//* if (position == exploreShopList.size() - 1) {
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
                }*//*
                fragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, fragment).commit();
            }
        });*/
    }
    @Override
    public int getItemCount() {
        return exploreShopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mExploreShopStatus;
        private ImageView mExploreShopImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mExploreShopStatus = itemView.findViewById(R.id.tv_explore_shop_status);
            mExploreShopImage = itemView.findViewById(R.id.image_view_explore_shop);
        }
    }
}
