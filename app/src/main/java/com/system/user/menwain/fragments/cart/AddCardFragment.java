package com.system.user.menwain.fragments.cart;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.UserCardsAdapter;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.cart.UserCardsResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCardFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserCardsAdapter userCardsAdapter;
    private List<UserCardsResponse.Cards.Datum> card_list = new ArrayList<UserCardsResponse.Cards.Datum>();
    private Preferences preferences;
    private ProgressDialog progressDialog;
    private CardView addCard;
    private TextView tvOrderTotla,tvShippingCost,tvTotal;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_card,container,false);
        preferences = new Preferences(getContext());
        customProgressDialog(getContext());
        getUserCards();

        tvOrderTotla = view.findViewById(R.id.tv_order_total_add_card);
        tvOrderTotla.setText(preferences.getTotalAmount()+"");
        tvShippingCost = view.findViewById(R.id.tv_shipping_cost_add_card);
        tvShippingCost.setText(preferences.getShippingCost()+"");
        tvTotal = view.findViewById(R.id.tv_total_add_card);
        tvTotal.setText(preferences.getTotalAmount()+preferences.getShippingCost()+"");

        addCard = view.findViewById(R.id.add_card);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentAddCard dialogFragmentAddCard = new DialogFragmentAddCard();
                dialogFragmentAddCard.show(getFragmentManager(),"add card");
            }
        });
        recyclerView = view.findViewById(R.id.recycler_view_cards);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userCardsAdapter = new UserCardsAdapter(getContext(),card_list);
        recyclerView.setAdapter(userCardsAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                int card_id = card_list.get(position).getId();
                deleteAddress(position, card_id);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }

    private void getUserCards() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.user_card_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UserCardsResponse userCardsResponse = gson.fromJson(response,UserCardsResponse.class);

                card_list.clear();
                for (int i = 0;i<userCardsResponse.getCards().getData().size();i++){
                    card_list.add(userCardsResponse.getCards().getData().get(i));
                }
                userCardsAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("card_error",error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headerMap = new HashMap<>();
                headerMap.put("Authorization","Bearer "+preferences.getToken());
                return headerMap;
            }

        };
        requestQueue.add(request);
    }

    private void deleteAddress(final int position, final int card_id) {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URLs.delete_card_url + card_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Card has been deleted.", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                card_list.remove(position);
                userCardsAdapter.notifyItemRemoved(position);
                userCardsAdapter.notifyItemRangeChanged(position, card_list.size());
                userCardsAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("card_delete_error", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + preferences.getToken());
                return headerMap;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
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
