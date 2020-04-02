package com.system.user.menwain.adapters.more_adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.more.stores.SelectedStoreFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder> {
    private String [] productsName;
    int [] stores;
    static Context context;
    public static int pos;
    private Prefrences prefrences;
    public StoresAdapter(Context context, String[] productsName, int[] stores) {
        this.productsName = productsName;
        this.context = context;
        this.stores = stores;
        prefrences = new Prefrences(context);
    }

    @NonNull
    @Override
    public StoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_stores,parent,false);
        StoresViewHolder categoryViewHolder = new StoresViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StoresViewHolder holder, final int position) {
        holder.mProductNameView.setText(productsName[position]);
        holder.mStore.setImageResource(stores[position]);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = holder.getAdapterPosition();
                prefrences.setMoreStoresFragStatus(2);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new SelectedStoreFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class StoresViewHolder extends RecyclerView.ViewHolder{
        TextView mProductNameView;
        ImageView mStore;
        private RatingBar ratingBar;
        public StoresViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.rating_bar_stores);
            mProductNameView=itemView.findViewById(R.id.store_name_view);
            mStore = itemView.findViewById(R.id.stores_image_view);
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            //stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.colorButton), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.yellowColor), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellowColor), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
