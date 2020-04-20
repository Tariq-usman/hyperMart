package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.fragments.cart.AvailNotAvailItemsListsFragment;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAvailabilityStoresAdapter extends RecyclerView.Adapter<ItemsAvailabilityStoresAdapter.ItemViewHolder> {
    private List<AvailNotAvailResponse.Datum> stores_list;
    private double lat, lang;
    Context context;
    Bundle bundle;
    Prefrences prefrences;
    private float distanceTo;

    public ItemsAvailabilityStoresAdapter(Context context, List<AvailNotAvailResponse.Datum> stores_list, double lat, double lang) {
        this.stores_list = stores_list;
        this.lat = lat;
        this.lang = lang;
        this.context = context;
        prefrences = new Prefrences(context);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_available_item_store, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        Glide.with(holder.mMartImageView.getContext()).load(stores_list.get(position).getImage()).into(holder.mMartImageView);

        getKmFromLatLong(lat, lang, Double.valueOf(stores_list.get(position).getLatitude()), Double.valueOf(stores_list.get(position).getLongitude()));
        String dist = distanceTo+"";
        String [] split_date = dist.split(".");
//        String first = split_date[0];
        holder.mDistanceView.setText(distanceTo+" km");
//        holder.mSortByPrice.setText(String.valueOf(price.get(position)));
        holder.mAvailableItems.setText(stores_list.get(position).getAvailable().size()+"");
        String pos = holder.mDistanceView.getText().toString();
//        int total = marts.length;
        holder.mTotalItems.setText("" + stores_list.get(position).getAvailable().size()+stores_list.get(position).getNotavailable().size());

        if (distanceTo <= 10) {
            holder.mStatusColorView.setBackgroundColor(Color.parseColor("#00c1bd"));
        } else if (distanceTo > 10 && distanceTo <= 15) {
            holder.mStatusColorView.setBackgroundColor(Color.parseColor("#FFFFEB3B"));
        } else {
            holder.mStatusColorView.setBackgroundColor(Color.parseColor("#FFF44336"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*editor.apply();
                editor.putInt("frag_status",2);*/
                prefrences.setCartFragStatus(3);
                AvailNotAvailItemsListsFragment fragment = new AvailNotAvailItemsListsFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                bundle = new Bundle();
                bundle.putString("price", holder.mSortByPrice.getText().toString());
                bundle.putString("distance", holder.mDistanceView.getText().toString());
                bundle.putString("image_url", String.valueOf(stores_list.get(position).getImage()));
                fragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores_list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mMartImageView;
        private TextView mDistanceView, mSortByPrice, mAvailableItems, mTotalItems;
        private View mStatusColorView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mMartImageView = itemView.findViewById(R.id.mart_logo_view);
            mDistanceView = itemView.findViewById(R.id.distance_view);
            mSortByPrice = itemView.findViewById(R.id.sort_by_price);
            mStatusColorView = itemView.findViewById(R.id.show_status_color_view);
            mAvailableItems = itemView.findViewById(R.id.available_items_view);
            mTotalItems = itemView.findViewById(R.id.total_items);
        }
    }

    public float getKmFromLatLong(double lat1, double lng1, double lat2, double lng2) {
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lng1);
        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lng2);
        float distanceInMeters = loc1.distanceTo(loc2);
        distanceTo = distanceInMeters / 1000;
        Log.e("distance", distanceInMeters / 1000 + " km");
        return distanceInMeters / 1000;
    }

}
