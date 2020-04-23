package com.system.user.menwain.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.responses.ProductDetailsResponse;
import com.system.user.menwain.responses.ReviewsResponse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemReviewsAdapter extends RecyclerView.Adapter<ItemReviewsAdapter.ItemReviewsViewHolder> {
    private List<ReviewsResponse.Dataa.Datum> reviewsList;

    public ItemReviewsAdapter(List<ReviewsResponse.Dataa.Datum> reviewsList) {
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

        holder.mUserName.setText(reviewsList.get(position).getCustomerreview().getFirstName());
        String date_time = reviewsList.get(position).getTime();;
        String[] split_date_time = date_time.split(" ");
        String date = split_date_time[0];
        holder.tvDate.setText(date);
        holder.tvRating.setText("("+reviewsList.get(position).getRating().toString()+")");
        holder.ratingBar.setRating(Float.valueOf(reviewsList.get(position).getRating()));
        holder.tvReview.setText(reviewsList.get(position).getReview());
    }

    @Override
    public int getItemCount() {
        if (reviewsList.size() > 0) {
            return reviewsList.size();
        } else {
            return 0;
        }
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
