package com.system.user.menwain.fragments.more.orders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.system.user.menwain.R;
import com.system.user.menwain.utils.URLs;

import java.util.HashMap;
import java.util.Map;

public class ItemReviewFragment extends Fragment {

    private TextView mSubmitReview, tvTitle, tvPrice, tvDate, tvTime, tvDescription;
    private RatingBar ratingBar;
    private EditText etReview;
    private ImageView mBackBtn, ivProduct;
    private Preferences prefrences;
    private Bundle bundle;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_review, container, false);
        prefrences = new Preferences(getContext());
        customProgressDialog(getContext());
        bundle = this.getArguments();


        mSubmitReview = view.findViewById(R.id.submit_review);
        etReview = view.findViewById(R.id.et_product_review);

        ivProduct = view.findViewById(R.id.iv_product_selected_product);
        tvTitle = view.findViewById(R.id.tv_title_selected_product);
        tvPrice = view.findViewById(R.id.tv_price_selected_product);
        tvDate = view.findViewById(R.id.tv_date_selected_product);
        tvTime = view.findViewById(R.id.tv_time_selected_product);
        tvDescription = view.findViewById(R.id.iv_description_selected_product);

        if (bundle != null) {
            ivProduct.setImageBitmap((Bitmap) bundle.getParcelable("image"));
            tvTitle.setText(bundle.getString("product_name"));
            tvPrice.setText(bundle.getString("price"));
           /* String date_time = bundle.getString("date");
            String[] split_date = date_time.split(" ");
            tvDate.setText(split_date[0]);
            tvTime.setText(split_date[1]);*/
            tvDescription.setText(bundle.getString("description"));

        }

        ratingBar = view.findViewById(R.id.rb_product);
//        mTitle = getActivity().findViewById(R.id.title_view);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mBackBtn.setVisibility(View.VISIBLE);

//        mTitle.setText("Item Review");

        mSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitProductRatingAndReivew();
               /* getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrderDetailsFragment()).addToBackStack(null).commit();
                mBackBtn.setVisibility(View.INVISIBLE);*/
//                startActivity(new Intent(getContext(),RateUsFragment.class));
//                finish();
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreOrdersFragStatus(2);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrderDetailsFragment()).addToBackStack(null).commit();
                mBackBtn.setVisibility(View.INVISIBLE);
//                finish();
            }
        });
        return view;
    }

    public void submitProductRatingAndReivew() {
        final int product_id = bundle.getInt("product_id");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.submit_product_review_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getContext(), "Rating has been added", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AllOrdersFragment()).addToBackStack(null).commit();
                mBackBtn.setVisibility(View.INVISIBLE);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("review_error", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + prefrences.getToken());
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("product_id", String.valueOf(product_id));
                map.put("rating", String.valueOf(ratingBar.getRating()));
                map.put("review", etReview.getText().toString().trim());
                return map;
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
