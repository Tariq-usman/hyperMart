package com.system.user.menwain.fragments.others;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.ItemReviewsAdapter;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.model.UpdateCartQuantity;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.Item_details_SlidingImages_Adapter;
import com.system.user.menwain.adapters.category_adapters.SelectedItemsFilterAdapter;
import com.system.user.menwain.fragments.category.CategoryFragment;
import com.system.user.menwain.fragments.home.HomeFragment;
import com.system.user.menwain.fragments.more.stores.SelectedStoreFragment;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.responses.ProductDetailsResponse;
import com.system.user.menwain.responses.ReviewsResponse;
import com.system.user.menwain.responses.home.HomeBannerResponse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDetailsFragment extends Fragment implements View.OnClickListener {
    private EditText etSearch;
    private List<ProductDetailsResponse.Data.Productpic> related_items_list = new ArrayList<ProductDetailsResponse.Data.Productpic>();
    private RecyclerView recyclerViewRelateItems;
    private SelectedItemsFilterAdapter selectedItemsFilterAdapter;
    private Item_details_SlidingImages_Adapter slidingImagesAdapter;
    private List<ReviewsResponse.Dataa.Datum> reviews_list = new ArrayList<ReviewsResponse.Dataa.Datum>();
    private RecyclerView recyclerViewItemReviews;
    private ItemReviewsAdapter itemReviewsAdapter;
    private CartViewModel cartViewModel;
    private UpdateCartQuantity updateCartQuantity;
    TextView mDescription, mSpecification, mReviews, tvDescription, tvSpecifications;
    private TextView tvPrice, tvStoreName, tvTitle, tvReviewsCount, tvQuantity, mAddToCart;
    private ImageView mBack, mSearch, mBarCodeScanner, ivIncreaseItem, ivDecreaseItem;
    private RatingBar ratingBar;
    Bundle bundle;
    private String status, productName, imagePath, storeName, price, quantity;
    private LinearLayout layoutSpecifications;
    private int productId, intQuantity;
    private static ViewPager mPager;
    private TabLayout tabLayout;
    private static int NUM_PAGES = 0;
    final long PERIOD_MS = 3000;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private CardView mSearchView;
    private Preferences prefrences;
    private Dialog dialog;
    private int product_id;
    final int[] count = {1};
    private Bitmap bitmap;
    private float totalPrice, unitPrice;
    private List<Integer> p_id_list = new ArrayList<Integer>();
    private List<HomeBannerResponse.Datum> bannersList = new ArrayList<>();
    ProductDetailsResponse detailsResponse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        prefrences = new Preferences(getContext());
        dialog = Utils.dialog(getContext());

        bundle = this.getArguments();
        if (bundle != null) {
            product_id = bundle.getInt("product_id");
            status = bundle.getString("status", "");
            getProductDetails(product_id);
            getProductReviews(product_id);
            getBannerData();
        } else {
            Toast.makeText(getContext(), "No product selected", Toast.LENGTH_SHORT).show();
        }
        mPager = view.findViewById(R.id.item_detail_pager);
        tabLayout = view.findViewById(R.id.tab_layout_item_details);
        tvDescription = view.findViewById(R.id.tv_description);
        tvSpecifications = view.findViewById(R.id.tv_specifications);

        tvPrice = view.findViewById(R.id.price_item_details);
        tvStoreName = view.findViewById(R.id.store_name_item_details);
        tvTitle = view.findViewById(R.id.title_item_details);
        tvReviewsCount = view.findViewById(R.id.reviews_count);
        tvQuantity = view.findViewById(R.id.quantity_item_details);
        ratingBar = view.findViewById(R.id.ratingBar_item_details);

        mSearch = view.findViewById(R.id.iv_search_details);
        etSearch = view.findViewById(R.id.et_search_details);
        mBarCodeScanner = view.findViewById(R.id.bar_code_scanner_details);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                SearchFragment fragment = new SearchFragment();
                if (etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim() == null) {
                    Toast.makeText(getContext(), getContext().getString(R.string.enter_desire_search), Toast.LENGTH_SHORT).show();
                } else {
                    bundle.putString("search", etSearch.getText().toString().trim());
                    etSearch.setText("");
                    fragment.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                }
            }
        });
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        ivDecreaseItem = view.findViewById(R.id.decrees_count_item_details);
        ivDecreaseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = tvQuantity.getText().toString();
                if (Integer.valueOf(num) > 1) {
                    count[0] = count[0] - 1;
                    tvQuantity.setText("" + count[0]);
                } else if (tvQuantity.getText().toString().length() == 0) {
                    tvQuantity.setText(0);
                }
            }
        });
        ivIncreaseItem = view.findViewById(R.id.increase_count_item_details);
        ivIncreaseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0] = count[0] + 1;
                tvQuantity.setText("" + count[0]);
            }
        });
        mDescription = view.findViewById(R.id.description_btn);
        mDescription.setOnClickListener(this);
        mSpecification = view.findViewById(R.id.specificatin_btn);
        mSpecification.setOnClickListener(this);
        mReviews = view.findViewById(R.id.reviews_btn);
        mReviews.setOnClickListener(this);
        mAddToCart = view.findViewById(R.id.add_to_cart_item_details);
        mAddToCart.setOnClickListener(this);
        mBack = view.findViewById(R.id.iv_back_items_details);
        mBack.setOnClickListener(this);

        slidingImagesAdapter = new Item_details_SlidingImages_Adapter(getContext(), bannersList);

        recyclerViewRelateItems = view.findViewById(R.id.recycler_view_related_items_item_details);
        recyclerViewRelateItems.setHasFixedSize(true);
        recyclerViewRelateItems.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        selectedItemsFilterAdapter = new SelectedItemsFilterAdapter(getContext(), related_items_list);
        recyclerViewRelateItems.setAdapter(selectedItemsFilterAdapter);

        recyclerViewItemReviews = view.findViewById(R.id.recycler_view_reviews);
        recyclerViewItemReviews.setHasFixedSize(true);
        recyclerViewItemReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        itemReviewsAdapter = new ItemReviewsAdapter(reviews_list);
        recyclerViewItemReviews.setAdapter(itemReviewsAdapter);

        init();
        return view;
    }

    private void getBannerData() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_banner_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HomeBannerResponse bannerResponse = gson.fromJson(response, HomeBannerResponse.class);
                bannersList.clear();
                for (int i = 0; i < bannerResponse.getData().size(); i++) {
                    bannersList.add(bannerResponse.getData().get(i));
                }
                slidingImagesAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("erroe", error.toString());
                dialog.dismiss();
            }
        });
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

    private void getProductReviews(int product_id) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.reviews_list_url + product_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ReviewsResponse reviewsResponse = gson.fromJson(response, ReviewsResponse.class);

                reviews_list.clear();
                for (int i = 0; i < reviewsResponse.getDataa().getData().size(); i++) {
                    reviews_list.add(reviewsResponse.getDataa().getData().get(i));
                }
                itemReviewsAdapter.notifyDataSetChanged();
                int review_count = reviews_list.size();
                tvReviewsCount.setText("(" + review_count + " Reviews)");
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("details_error", error.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("X-Language", prefrences.getLanguage());
                return header;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void getProductDetails(int product_id) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_product_details_url + product_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    detailsResponse = gson.fromJson(response, ProductDetailsResponse.class);
                    productId = detailsResponse.getData().getId();
                    tvPrice.setText(detailsResponse.getData().getAvgPrice().toString());
                    tvTitle.setText(detailsResponse.getData().getName());
                    tvDescription.setText(detailsResponse.getData().getDescription());
                    tvSpecifications.setText(detailsResponse.getData().getSpecification());
                    if (detailsResponse.getRating() == null || detailsResponse.getRating() == "" || detailsResponse.getRating().isEmpty()) {
                        ratingBar.setRating(0);
                    } else {
                        ratingBar.setRating(Float.valueOf(detailsResponse.getRating()));
                    }
                    related_items_list.clear();
                    for (int i = 0; i < detailsResponse.getData().getProductpic().size(); i++) {
                        related_items_list.add(detailsResponse.getData().getProductpic().get(i));
                    }
                    selectedItemsFilterAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("exception_error", e.getMessage());
                    Toast.makeText(getContext(), "" + getString(R.string.no_details_found), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("details_error", error.toString());
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
                Map<String, String> header = new HashMap<>();
//                header.put("Authentication", "Bearer " + prefrences.getToken());
                header.put("X-Language", prefrences.getLanguage());
                header.put("Accept", "application/json");
                return header;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void init() {
        mPager.setAdapter(slidingImagesAdapter);
        tabLayout.setupWithViewPager(mPager, true);
        NUM_PAGES = bannersList.size();
        /*After setting the adapter use the timer */
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (bannersList.size() == currentPage) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                mPager.setCurrentItem(currentPage, true);
                handler.postDelayed(this, PERIOD_MS);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, PERIOD_MS);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.description_btn) {
            if (detailsResponse.getData().getDescription() == null) {
                tvDescription.setText(getString(R.string.no_description_found));
            }
            tvDescription.setVisibility(View.VISIBLE);
            tvSpecifications.setVisibility(View.GONE);
            recyclerViewItemReviews.setVisibility(View.GONE);
            mDescription.setTextColor(getResources().getColor(R.color.lightBlueColor));
            mSpecification.setTextColor(getResources().getColor(R.color.darkGreenColor));
            mReviews.setTextColor(getResources().getColor(R.color.darkGreenColor));
        } else if (id == R.id.specificatin_btn) {
            if (detailsResponse.getData().getSpecification() == null) {
                tvSpecifications.setText(getString(R.string.no_specs_found));
            }
            tvDescription.setVisibility(View.GONE);
            tvSpecifications.setVisibility(View.VISIBLE);
            recyclerViewItemReviews.setVisibility(View.GONE);
            mSpecification.setTextColor(getResources().getColor(R.color.lightBlueColor));
            mDescription.setTextColor(getResources().getColor(R.color.darkGreenColor));
            mReviews.setTextColor(getResources().getColor(R.color.darkGreenColor));
        } else if (id == R.id.reviews_btn) {
            if (detailsResponse.getData().getReviewss().size() == 0) {
                Toast.makeText(getContext(), "" + getString(R.string.no_reviews_found), Toast.LENGTH_SHORT).show();
            }
            tvDescription.setVisibility(View.GONE);
            tvSpecifications.setVisibility(View.GONE);
            recyclerViewItemReviews.setVisibility(View.VISIBLE);
            mReviews.setTextColor(getResources().getColor(R.color.lightBlueColor));
            mDescription.setTextColor(getResources().getColor(R.color.darkGreenColor));
            mSpecification.setTextColor(getResources().getColor(R.color.darkGreenColor));
        } else if (id == R.id.add_to_cart_item_details) {
            if (detailsResponse == null) {
                Toast.makeText(getContext(), "" + getString(R.string.no_details_found), Toast.LENGTH_SHORT).show();
            } else {
                try {
                    if (detailsResponse.getData().getImage() == null) {
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logopng);
                    } else {
                        dialog.show();
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        URL url = new URL(detailsResponse.getData().getImage());
                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        Log.e("bitmap", bitmap.toString());
                        dialog.dismiss();
                    }
                } catch (IOException e) {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logopng);
                    e.printStackTrace();
                    dialog.dismiss();
                }

                productName = tvTitle.getText().toString();
                storeName = "";
                price = tvPrice.getText().toString();
                quantity = tvQuantity.getText().toString();
                totalPrice = Float.parseFloat(price);
                intQuantity = Integer.parseInt(quantity);
                unitPrice = totalPrice * intQuantity;
                saveToInternalStorage(bitmap);
                cartViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(CartViewModel.class);
                Cart cart = new Cart(productId, imagePath, productName, storeName, totalPrice, unitPrice, intQuantity);
                cartViewModel.getCartDataList().observe((FragmentActivity) getContext(), new Observer<List<Cart>>() {
                    @Override
                    public void onChanged(List<Cart> carts) {

                        for (int i = 0; i < carts.size(); i++) {
                            p_id_list.add(carts.get(i).getP_id());
                        }
                    }
                });
                if (p_id_list.size() == 0) {
                    cartViewModel.insertCart(cart);
                    Toast.makeText(getContext(), getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
                } else {

                    for (int i = 0; i < p_id_list.size(); i++) {
                        if (p_id_list.get(i) == productId) {
                            id = p_id_list.get(i);
                        }
                    }
                    if (id == productId) {
                        updateCartQuantity = new UpdateCartQuantity(productId, intQuantity, unitPrice);
                        cartViewModel.updateCartQuantity(updateCartQuantity);
                        Toast.makeText(getContext(), getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                    } else {
                        cartViewModel.insertCart(cart);
                        Toast.makeText(getContext(), getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } else if (id == R.id.iv_back_items_details) {
            if (status == "1" || status == "3") {
                prefrences.setHomeFragStatus(0);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "Home").addToBackStack(null).commit();
            } else if (status == "2") {
                prefrences.setCategoryFragStatus(1);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CategoryFragment()).addToBackStack(null).commit();
            } else {
                prefrences.setMoreStoresFragStatus(2);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SelectedStoreFragment()).addToBackStack(null).commit();

            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, productName + ".jpg");
        imagePath = String.valueOf(mypath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


}
