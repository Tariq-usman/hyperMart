package com.system.user.menwain.adapters.more_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.responses.more.stores.SelectedStoreResponse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectedStoreCategoryAdapter extends RecyclerView.Adapter<SelectedStoreCategoryAdapter.SelectedStoreViewHolder> {
    private LayoutInflater layoutInflater;
    Context context;
    List<SelectedStoreResponse.Category> category_list;
    int lastPosition = -1;
    int abc = StoresAdapter.pos;
    private boolean check = false;
    private RecyclerClickInterface clickInterface;

    public SelectedStoreCategoryAdapter(Context context, List<SelectedStoreResponse.Category> category_list, RecyclerClickInterface clickInterface) {
        this.context = context;
        this.clickInterface = clickInterface;
        this.category_list = category_list;
    }

    @NonNull
    @Override
    public SelectedStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_store, parent, false);
        SelectedStoreViewHolder categoryViewHolder = new SelectedStoreViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectedStoreViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.mStoreNameView.setText(category_list.get(position).getDescription());
        Glide.with(holder.mStoreView.getContext()).load(category_list.get(position).getPicture()).into(holder.mStoreView);
        holder.setIsRecyclable(true);
        if (lastPosition == position) {
            holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
            clickInterface.interfaceOnClick(holder.getView(), position);
        } else {
            holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_out));
        }

    }

    @Override
    public int getItemCount() {
        return category_list.size();
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
