package com.system.user.menwain.fragments.more.menu_fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.more.MoreFragment;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.more.OverAllRatingResponse;
import com.system.user.menwain.utils.URLs;

import java.util.HashMap;
import java.util.Map;

public class RateUsFragment extends Fragment {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private TextView mConfirmBtn, mTitle;
    private ImageView mBackBtn;
    private RatingBar ratingBarStore,ratingBarDeliveryBo,ratingBarServices;
    private Preferences prefrences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_us,container,false);
        customDialog(getContext());
        prefrences = new Preferences(getContext());
        mConfirmBtn = view.findViewById(R.id.submit);
        ratingBarStore = view.findViewById(R.id.rating_bar_store);
        ratingBarDeliveryBo = view.findViewById(R.id.rating_bar_delivery_boy);
        ratingBarServices= view.findViewById(R.id.rating_bar_overall_services);
        mBackBtn = view.findViewById(R.id.iv_back_rate);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postRating();
               /* startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();*/
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new MoreFragment()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    public void postRating() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.over_all_rating_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                OverAllRatingResponse ratingResponse = gson.fromJson(response, OverAllRatingResponse.class);
                Toast.makeText(getContext(), ""+ ratingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "rating_error", error.toString());
                dialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headerMap = new HashMap<>();
                headerMap.put("Authorization","Bearer " + prefrences.getToken());
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("store_rating",String.valueOf(ratingBarStore.getRating()));
                map.put("deliveryboy_rating",String.valueOf(ratingBarDeliveryBo.getRating()));
                map.put("servicerating",String.valueOf(ratingBarServices.getRating()));
                return map;
            }
        };
        requestQueue.add(request);
    }
    public void customDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.layout_loading_dialog);
        }
        dialog = builder.create();
    }

}
