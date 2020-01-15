package com.system.user.menwain.adapters.more_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SelectedStoreAdapter extends RecyclerView.Adapter<SelectedStoreAdapter.SelectedStoreViewHolder> {
    private LayoutInflater layoutInflater;
    private String[] storesName;
    Context context;
    private int[] stores;
    int lastPosition = 0;
    int abc = StoresAdapter.pos;
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
        holder.setIsRecyclable(false);
        holder.mStoreNameView.setText(storesName[position]);
        holder.mStoreView.setImageResource(stores[position]);
        holder.setIsRecyclable(true);


       /* if (check == false) {
            if (abc == position) {
                holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
                check = true;
                holder.setIsRecyclable(true);
            } else {
                holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_out));
            }
        } else */if (lastPosition == position) {
            holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
        } else {
            holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_out));
        }

         /*position = position - 1;
        position = position % storesName.length;

      if (position == -1) {
            holder.mStoreNameView.setText("");
            holder.mStoreView.setVisibility(View.INVISIBLE);
            position++;
        } else if (position >= 0) {
         if (position == abc){
                holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
            }else if (abc !=position){
                holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_out));
            }
        }*/
    }

    @Override
    public int getItemCount() {
        return storesName.length;
        //return Integer.MAX_VALUE;
    }

    public class SelectedStoreViewHolder extends RecyclerView.ViewHolder {
        private TextView mStoreNameView;
        private CircleImageView mStoreView;

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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
