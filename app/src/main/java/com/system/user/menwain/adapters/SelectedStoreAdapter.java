package com.system.user.menwain.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SelectedStoreAdapter extends RecyclerView.Adapter<SelectedStoreAdapter.SelectedStoreViewHolder> {
    private String[] storesName;
    Context context;
    private int[] stores;
    int lastPosition = -1;
    int abc;
    private boolean check = false;

    public SelectedStoreAdapter(String[] storesName, Context context, int[] stores) {
        this.storesName = storesName;
        this.context = context;
        this.stores = stores;
    }

    @NonNull
    @Override
    public SelectedStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_store, parent, false);
        SelectedStoreViewHolder categoryViewHolder = new SelectedStoreViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectedStoreViewHolder holder, int position) {
        holder.mStoreNameView.setText(storesName[position]);
        holder.mStoreView.setImageResource(stores[position]);

        abc = StoresAdapter.pos;
        Log.e("check", abc + "");
        //for (int i = 0; i <storesName.length; i++){
        if (check == false) {
            if (abc == position) {
                check = true;
                holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
            } else {
                holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_out));
            }
        } else if (lastPosition == position) {
            holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
        } else {
            holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_out));
        }
        // }
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abc = holder.getAdapterPosition();
                holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));

            }
        });*/


    }

    @Override
    public int getItemCount() {
        return storesName.length;
    }

    public class SelectedStoreViewHolder extends RecyclerView.ViewHolder {
        TextView mStoreNameView;
        CircleImageView mStoreView;

        public SelectedStoreViewHolder(@NonNull View itemView) {
            super(itemView);
            mStoreView = itemView.findViewById(R.id.selected_store_view);
            mStoreNameView = itemView.findViewById(R.id.selected_store_name_view);
            mStoreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastPosition = getAdapterPosition();
//                    abc = lastPosition;
//                    getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
                    notifyDataSetChanged();
                }
            });
        }

        public View getView() {
            return itemView;
        }
    }
}
