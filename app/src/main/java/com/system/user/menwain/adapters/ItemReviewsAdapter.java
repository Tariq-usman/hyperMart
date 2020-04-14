package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.responses.ProductDetailsResponse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemReviewsAdapter extends RecyclerView.Adapter<ItemReviewsAdapter.ItemReviewsViewHolder> {
    private List<ProductDetailsResponse.Data.Reviews> reviewsList;

    public ItemReviewsAdapter(List<ProductDetailsResponse.Data.Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }

    @NonNull
    @Override
    public ItemReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_reviews, parent, false);
        ItemReviewsViewHolder categoryViewHolder = new ItemReviewsViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemReviewsViewHolder holder, int position) {
        String date = reviewsList.get(position).getCreatedAt();
        String [] split_date = date.split(":");
        holder.tvDate.setText(split_date[0]);
        holder.tvRating.setText(reviewsList.get(position).getRating().toString());
        holder.ratingBar.setRating(Float.valueOf(reviewsList.get(position).getRating()));
        holder.tvReview.setText(reviewsList.get(position).getReview());
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                             FragmentManager fragmentManager =   ((AppCompatActivity)context).getSupportFragmentManager();
                             fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new ItemsFragment()).addToBackStack(null).commit();
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new ItemsFragment();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public static class ItemReviewsViewHolder extends RecyclerView.ViewHolder {
        private TextView mUserName, tvDate, tvRating, tvReview;
        private RatingBar ratingBar;

        public ItemReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.user_name_view);
            tvDate = itemView.findViewById(R.id.tv_date_product_reviews);
            ratingBar = itemView.findViewById(R.id.ratingBar_product_reviews);
            tvRating = itemView.findViewById(R.id.tv_rating_product_reviews);
            tvReview = itemView.findViewById(R.id.tv_reviews_product_reviews);
        }
    }
}
