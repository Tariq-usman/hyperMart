package com.system.user.menwain.adapters.my_lists_adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.fragments.my_list.ListDetailsFragment;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.more.stores.StoresAllBranchesResponse;
import com.system.user.menwain.responses.my_list.UserWishlistProductsResponse;
import com.system.user.menwain.utils.URLs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllListsAdapter extends RecyclerView.Adapter<AllListsAdapter.AllListsViewHolder> {

    private List<UserWishlistProductsResponse.Product.Datum> orders_list;
    Context context;
    Preferences prefrences;
    private Bundle bundle;
    private ProgressDialog progressDialog;

    public AllListsAdapter(Context context, List<UserWishlistProductsResponse.Product.Datum> orders_list) {
        this.orders_list = orders_list;
        this.context = context;
        prefrences = new Preferences(context);
        customProgressDialog(context);
    }


    @NonNull
    @Override
    public AllListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_lists, parent, false);
        AllListsViewHolder allListsViewHolder = new AllListsViewHolder(view);
        return allListsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AllListsViewHolder holder, final int position) {
        holder.mListName.setText(orders_list.get(position).getWishlistName().toString());
        String date = orders_list.get(position).getDateTime();
        String [] split_date = date.split(" ");
        holder.tvDate.setText(split_date[0]);
        holder.tvPrice.setText(orders_list.get(position).getTotalAmount().toString());
        holder.viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                prefrences.setMyListFragStatus(1);
                ListDetailsFragment fragment = new ListDetailsFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                bundle.putInt("list_id", orders_list.get(position).getId());
                bundle.putString("list_name", orders_list.get(position).getWishlistName());
                fragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });
        holder.shareList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, orders_list.get(position).getShareLink());
                context.startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int list_id = orders_list.get(position).getId();
                int pos =holder.getAdapterPosition();
                deleteWistList(list_id,pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders_list.size();
    }

    public static class AllListsViewHolder extends RecyclerView.ViewHolder {
        private TextView mListName, tvDate, tvPrice,viewList;
        private ImageView ivDelete,shareList;

        public AllListsViewHolder(@NonNull View itemView) {
            super(itemView);
            mListName = itemView.findViewById(R.id.list_name_view);
            tvDate = itemView.findViewById(R.id.tv_date_my_list);
            tvPrice = itemView.findViewById(R.id.tv_price_my_list);
            viewList = itemView.findViewById(R.id.view_list);
            ivDelete = itemView.findViewById(R.id.iv_delete_my_list);
            shareList = itemView.findViewById(R.id.iv_share_list);
        }
    }

    private void deleteWistList(int list_id, final int pos) {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.delete_wish_list + list_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                orders_list.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, orders_list.size());
                notifyDataSetChanged();
                Toast.makeText(context, "List Delete Successfully.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wishError", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + prefrences.getToken());
                return headerMap;
            }
        };
        requestQueue.add(request);
    }
    public void customProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Fetching max value
        progressDialog.getMax();
    }

}
