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
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.fragments.cart.AvailNotAvailItemsListsFragment;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAvailabilityStoresAdapter extends RecyclerView.Adapter<ItemsAvailabilityStoresAdapter.ItemViewHolder> {
    private List<AvailNotAvailResponse.Datum> stores_list;
    private double lat, lang;
    private int total_amount = 0;
    private int total_amount_not_avail = 0;
    Context context;
    Bundle bundle;
    Preferences prefrences;
    private float distanceTo;
    public static List<AvailNotAvailResponse.Datum.Available> available_list;
    public static List<AvailNotAvailResponse.Datum.Notavailable> not_available_list;
    private static DecimalFormat decimalFormat;


    public ItemsAvailabilityStoresAdapter(Context context, List<AvailNotAvailResponse.Datum> stores_list, double lat, double lang) {
        this.stores_list = stores_list;
        this.lat = lat;
        this.lang = lang;
        this.context = context;
        prefrences = new Preferences(context);
        available_list = new ArrayList<>();
        not_available_list = new ArrayList<>();
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
        decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.UP);
        holder.mDistanceView.setText(decimalFormat.format(distanceTo));
        for (int i = 0; i < stores_list.get(position).getAvailable().size(); i++) {
            total_amount = total_amount + stores_list.get(position).getAvailable().get(i).getStoreprice();
        }
        holder.mSortByPrice.setText(String.valueOf(total_amount));
        total_amount = 0;

        holder.mAvailableItems.setText(stores_list.get(position).getAvailable().size() + "");
        String pos = holder.mDistanceView.getText().toString();
        holder.mTotalItems.setText(stores_list.get(position).getAvailable().size() + stores_list.get(position).getNotavailable().size() + "");

        if (distanceTo <= 10) {
            holder.mStatusColorView.setBackgroundColor(Color.parseColor("#00c1bd"));
        } else if (distanceTo > 10 && distanceTo <= 15) {
            holder.mStatusColorView.setBackgroundColor(Color.parseColor("#FFFFEB3B"));
        } else {
            holder.mStatusColorView.setBackgroundColor(Color.parseColor("#FFF44336"));
        }

        if (holder.mSortByPrice.getText().toString() == "0") {
            for (int i = 0; i < stores_list.get(position).getNotavailable().size(); i++) {
                total_amount_not_avail = total_amount_not_avail + stores_list.get(position).getNotavailable().get(i).getHighestPrice();
            }
            holder.mSortByPrice.setText(String.valueOf(total_amount_not_avail));
            total_amount_not_avail = 0;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*ItemsAvailabilityStoresRadiusAdapter.available_list.clear();
                ItemsAvailabilitySelectedStoresAdapter.available_list.clear();*/
                prefrences.setCartFragStatus(3);
                prefrences.setStoreId(stores_list.get(position).getId());
                prefrences.setStoreName(stores_list.get(position).getName());
                prefrences.setTotalPrice(total_amount);
                AvailNotAvailItemsListsFragment fragment = new AvailNotAvailItemsListsFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                bundle = new Bundle();
                bundle.putInt("store_id", stores_list.get(position).getId());
                bundle.putString("price", holder.mSortByPrice.getText().toString());
                bundle.putString("available", stores_list.get(position).getAvailable().size() + "");
                bundle.putString("not_available", stores_list.get(position).getNotavailable().size() + "");
                bundle.putString("distance", holder.mDistanceView.getText().toString());
                bundle.putString("image_url", String.valueOf(stores_list.get(position).getImage()));
                for (int i = 0; i < stores_list.get(position).getAvailable().size(); i++) {
                    available_list.add(stores_list.get(position).getAvailable().get(i));
                }
                for (int i = 0; i < stores_list.get(position).getNotavailable().size(); i++) {
                    not_available_list.add(stores_list.get(position).getNotavailable().get(i));
                }
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
