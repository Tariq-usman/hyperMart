package com.system.user.menwain.fragments.my_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.fragments.cart.DeliveryAddressFragment;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentTimeSlots;
import com.system.user.menwain.others.PaginationListenerGridLayoutManager;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.my_lists_adapters.ListDetailsAdapter;
import com.system.user.menwain.responses.my_list.WistListByIdResopnse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.system.user.menwain.others.PaginationListenerGridLayoutManager.PAGE_START;

public class ListDetailsFragment extends Fragment implements View.OnClickListener {
    private ImageView mCart, mFavourite, mHome, mCategory, mMore, mCloseBtn, mBackBtnPay;
    private TextView mConfirm, tvHome, tvCategory, tvCart, tvMore, tvFavourite;
    private RecyclerView recyclerViewListDetails;
    private ListDetailsAdapter listDetailsAdapter;
    private GridLayoutManager gridLayoutManager;
    private FloatingActionButton floatingActionButton;
    private TextView mConfirmBtn, tvListName;
    private ImageView mBackBtn;
    private Preferences prefrences;
    private Bundle bundle;
    private Dialog dialog;
    private List<WistListByIdResopnse.Datum> products_list = new ArrayList<>();
    private String list_name;
    public static List<Integer> reorder_list = new ArrayList<Integer>();
    private WistListByIdResopnse listByIdResopnse;
    private int list_id;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int itemCount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_details, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        prefrences = new Preferences(getContext());
        dialog = Utils.dialog(getContext());
        bundle = this.getArguments();
        if (bundle != null) {
            list_id = bundle.getInt("list_id", 0);
            list_name = bundle.getString("list_name", "");
            getProductList(list_id);
        } else {

        }
        mHome = getActivity().findViewById(R.id.home_view);
        tvHome = getActivity().findViewById(R.id.tv_home_view);
        mCategory = getActivity().findViewById(R.id.category_view);
        tvCategory = getActivity().findViewById(R.id.tv_category_view);

        mCart = getActivity().findViewById(R.id.cart);
        tvCart = getActivity().findViewById(R.id.tv_cart);

        mFavourite = getActivity().findViewById(R.id.favourite_view);
        tvFavourite = getActivity().findViewById(R.id.tv_favourite_view);

        mMore = getActivity().findViewById(R.id.more);
        tvMore = getActivity().findViewById(R.id.tv_more);
        mConfirmBtn = view.findViewById(R.id.confirm_btn_list_details);
        tvListName = view.findViewById(R.id.tv_list_name);
        tvListName.setText(list_name);
        mBackBtn = view.findViewById(R.id.iv_back_my_list_details);

        mConfirmBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);

        recyclerViewListDetails = view.findViewById(R.id.recycler_view_list_details);
        recyclerViewListDetails.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
        recyclerViewListDetails.setLayoutManager(gridLayoutManager);
        listDetailsAdapter = new ListDetailsAdapter(getContext(), products_list);
        recyclerViewListDetails.setAdapter(listDetailsAdapter);
        recyclerViewListDetails.addOnScrollListener(new PaginationListenerGridLayoutManager(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                getProductList(list_id);
            }

            @Override
            protected boolean isLastPage() {
                return isLastPage;
            }

            @Override
            protected boolean isLoading() {
                return isLoading;
            }
        });
        return view;
    }

    private void getProductList(int list_id) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_user_wish_list_by_id + list_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listByIdResopnse = gson.fromJson(response, WistListByIdResopnse.class);
                    products_list.clear();
                    for (int i = 0; i < listByIdResopnse.getData().size(); i++) {
                        products_list.add(listByIdResopnse.getData().get(i));
                    }
                    listDetailsAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wishError", error.toString());
                try {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(getContext(), getString(R.string.network_timeout), Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getContext(), getString(R.string.authentication_error), Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        Toast.makeText(getContext(), getString(R.string.no_network_found), Toast.LENGTH_LONG).show();
                    } else {
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.confirm_btn_list_details) {
            prefrences.setOrderStatus(2);
            prefrences.setWishListName(listByIdResopnse.getDetails().getWishlistName());
            prefrences.setTotalAmount(listByIdResopnse.getDetails().getTotalAmount());
            prefrences.setStoreId(listByIdResopnse.getDetails().getStoreId());
            prefrences.setDeliveryAddressId(listByIdResopnse.getDetails().getAddressId());
            prefrences.setShippingId(listByIdResopnse.getDetails().getShippingMethodId());
            prefrences.setShippingCost(listByIdResopnse.getDetails().getShippingCost());
            prefrences.setOrderId(listByIdResopnse.getDetails().getId());
            prefrences.setPaymentStatus(listByIdResopnse.getDetails().getPaymentMethodId());
            reorder_list.clear();
            for (int i = 0; i < products_list.size(); i++) {
                reorder_list.add(products_list.get(i).getId());
            }
            getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DialogFragmentTimeSlots()).addToBackStack(null).commit();
        } else if (id == R.id.iv_back_my_list_details) {
            prefrences.setMyListFragStatus(0);
            getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AllListsFragment()).addToBackStack(null).commit();
        }

    }
}
