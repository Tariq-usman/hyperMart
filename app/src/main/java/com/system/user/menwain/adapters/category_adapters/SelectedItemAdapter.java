package com.system.user.menwain.adapters.category_adapters;

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

public class SelectedItemAdapter extends RecyclerView.Adapter<SelectedItemAdapter.SelectedItemViewHolder> {
    private String[] productsName;
    Context context;
    private int[] items;
    public int lastPosition = -1;
    private boolean check = false;
    private int passedPosition = CategoryAdapter.passId;

    public SelectedItemAdapter(Context context, String[] productsName, int[] items) {
        this.productsName = productsName;
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_items, parent, false);
        SelectedItemViewHolder categoryViewHolder = new SelectedItemViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.mProductNameView.setText(productsName[position]);
        holder.mProduct.setImageResource(items[position]);
        if (check == false) {
            if (passedPosition == position) {
                holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
                check = true;
                holder.setIsRecyclable(true);
            } else {
                holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_out));
            }
        } else if (lastPosition == position) {
            holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
        } else {
            holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_out));
        }
       /* position = position - 1;
        position = position % productsName.length;

        if (position == -1) {
            holder.mProductNameView.setText("");
            holder.mProduct.setVisibility(View.INVISIBLE);
            position++;
        } else if (position >= 0) {
            holder.mProductNameView.setText(productsName[position]);
            holder.mProduct.setImageResource(items[position]);
        }*/

    }

    @Override
    public int getItemCount() {
        return items.length;
//        return Integer.MAX_VALUE;
    }

    public class SelectedItemViewHolder extends RecyclerView.ViewHolder {
        TextView mProductNameView;
        CircleImageView mProduct;

        public SelectedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mProduct = itemView.findViewById(R.id.selected_product_view);
            mProductNameView = itemView.findViewById(R.id.product_name_view);
            mProduct.setOnClickListener(new View.OnClickListener() {
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
