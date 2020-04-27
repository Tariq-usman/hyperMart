package com.system.user.menwain.adapters.category_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.category.CategoryItemsFragment;
import com.system.user.menwain.fragments.category.SubCategoryItemsFragment;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.category.CategoryResponse;
import com.system.user.menwain.responses.category.SuperCategoryResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.SelectedItemViewHolder> {
    Context context;
    List<CategoryResponse.Category.Datum> catergoryList;
    public int lastPosition = -1;
    private boolean check = false;
    private int passedPosition = SuperCategoryAdapter.passId;
    RecyclerClickInterface clickInterface;
    Preferences preferences;

    public CategoryAdapter(Context context, List<CategoryResponse.Category.Datum> catergoryList, RecyclerClickInterface clickInterface) {
        this.catergoryList = catergoryList;
        this.context = context;
        preferences = new Preferences(context);
        this.clickInterface = clickInterface;

    }

    @NonNull
    @Override
    public SelectedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_items, parent, false);
        SelectedItemViewHolder categoryViewHolder = new SelectedItemViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.mProductNameView.setText(catergoryList.get(position).getDescription());
        Glide.with(holder.mProduct.getContext()).load(catergoryList.get(position).getPicture()).into(holder.mProduct);
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
            //clickInterface.interfaceOnClick(holder.getView(), position);
            preferences.setCatsId(catergoryList.get(position).getId());
            preferences.setCategoryFragStatus(2);
            SubCategoryItemsFragment fragment = new SubCategoryItemsFragment();
            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, fragment).commit();
        } else {
            holder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_out));
        }


    }

    @Override
    public int getItemCount() {
        return catergoryList.size();
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
