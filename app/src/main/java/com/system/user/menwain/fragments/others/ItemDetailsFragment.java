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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.adapters.ItemReviewsAdapter;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.model.UpdateCartQuantity;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.Item_details_SlidingImages_Adapter;
import com.system.user.menwain.adapters.category_adapters.SelectedItemsFilterAdapter;
import com.system.user.menwain.fragments.category.CategoryItemsFragment;
import com.system.user.menwain.fragments.home.HomeFragment;
import com.system.user.menwain.fragments.more.stores.SelectedStoreFragment;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.responses.ProductDetailsResponse;
import com.system.user.menwain.responses.ReviewsResponse;
import com.system.user.menwain.utils.URLs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ItemDetailsFragment extends Fragment implements View.OnClickListener {
    private int[] IMAGES = {R.drawable.dis, R.drawable.disc, R.drawable.disco};
    private List<ProductDetailsResponse.Data.Productpic> related_items_list = new ArrayList<ProductDetailsResponse.Data.Productpic>();
    private RecyclerView recyclerViewRelateItems;
    private SelectedItemsFilterAdapter selectedItemsFilterAdapter;

    private List<ReviewsResponse.Dataa.Datum> reviews_list = new ArrayList<ReviewsResponse.Dataa.Datum>();
    private RecyclerView recyclerViewItemReviews;
    private ItemReviewsAdapter itemReviewsAdapter;
    private CartViewModel cartViewModel;

    TextView mDescription, mSpecification, mReviews, tvDescription, tvSpecifications;
    private TextView tvPrice, tvStoreName, tvTitle, tvReviewsCount, tvQuantity, mAddToCart;
    private ImageView mBack, mBarCodeScanner, ivIncreaseItem, ivDecreaseItem;
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
    private ProgressDialog progressDialog;
    private int product_id;
    final int[] count = {1};
    private Bitmap bitmap;
    private float totalPrice, unitPrice;
    private List<Integer> p_id_list = new ArrayList<Integer>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        prefrences = new Preferences(getContext());
        customProgressDialog(getContext());

        bundle = this.getArguments();
        if (bundle != null) {
            product_id = bundle.getInt("product_id");
            status = bundle.getString("status", "");
            getProductDetails(product_id);
            getProductReviews(product_id);
        } else {
            Toast.makeText(getContext(), "No product selected", Toast.LENGTH_SHORT).show();
        }
        /*IMAGES = new int[]{(Integer.parseInt(bundle.getString("image_url", ""))),
                (Integer.parseInt(bundle.getString("image_url1", ""))),
                (Integer.parseInt(bundle.getString("image_url2", "")))};*/
        mSearchView = getActivity().findViewById(R.id.search_view);
        //mSearchView.setVisibility(View.INVISIBLE);
        mPager = view.findViewById(R.id.item_detail_pager);
        tabLayout = view.findViewById(R.id.tab_layout_item_details);
        tvDescription = view.findViewById(R.id.tv_description);
        tvSpecifications = view.findViewById(R.id.tv_specifications);
        //layoutSpecifications = view.findViewById(R.id.layout_specification);

        mBarCodeScanner = getActivity().findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        tvPrice = view.findViewById(R.id.price_item_details);
        tvStoreName = view.findViewById(R.id.store_name_item_details);
        tvTitle = view.findViewById(R.id.title_item_details);
        tvReviewsCount = view.findViewById(R.id.reviews_count);
        tvQuantity = view.findViewById(R.id.quantity_item_details);
        ratingBar = view.findViewById(R.id.ratingBar_item_details);

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
        mBack = getActivity().findViewById(R.id.iv_back);
        mBack.setVisibility(View.VISIBLE);
        mBack.setOnClickListener(this);


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

    private void getProductReviews(int product_id) {
        progressDialog.show();
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
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("details_error", error.toString());
                progressDialog.dismiss();
            }
        });
        requestQueue.add(request);
    }

    private void getProductDetails(int product_id) {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_product_details_url + product_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ProductDetailsResponse detailsResponse = gson.fromJson(response, ProductDetailsResponse.class);
                productId = detailsResponse.getData().getId();
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    URL url = new URL(detailsResponse.getData().getImage());
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Log.e("bitmap", bitmap.toString());

                    tvPrice.setText(detailsResponse.getData().getAvgPrice().toString());
                    tvTitle.setText(detailsResponse.getData().getName());
                    tvDescription.setText(detailsResponse.getData().getDescription());
                    tvSpecifications.setText(detailsResponse.getData().getSpecification());
                    if (detailsResponse.getRating() == null || detailsResponse.getRating()=="" || detailsResponse.getRating().isEmpty()) {
                        ratingBar.setRating(0);
                    } else {
                        ratingBar.setRating(Float.valueOf(detailsResponse.getRating()));
                    }
                    int review_count = reviews_list.size();
                    tvReviewsCount.setText("(" + review_count + " Reviews)");

                    related_items_list.clear();
                    for (int i = 0; i < detailsResponse.getData().getProductpic().size(); i++) {
                        related_items_list.add(detailsResponse.getData().getProductpic().get(i));
                    }
                    selectedItemsFilterAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("details_error", error.toString());
                progressDialog.dismiss();
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

    private void init() {
        mPager.setAdapter(new Item_details_SlidingImages_Adapter(getContext(), IMAGES));
        tabLayout.setupWithViewPager(mPager, true);
        NUM_PAGES = IMAGES.length;
        /*After setting the adapter use the timer */
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (IMAGES.length == currentPage) {
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
            tvDescription.setVisibility(View.VISIBLE);
            tvSpecifications.setVisibility(View.GONE);
            recyclerViewItemReviews.setVisibility(View.GONE);
            mDescription.setTextColor(getResources().getColor(R.color.lightBlueColor));
            mSpecification.setTextColor(getResources().getColor(R.color.darkGreenColor));
            mReviews.setTextColor(getResources().getColor(R.color.darkGreenColor));
        } else if (id == R.id.specificatin_btn) {
            tvDescription.setVisibility(View.GONE);
            tvSpecifications.setVisibility(View.VISIBLE);
            recyclerViewItemReviews.setVisibility(View.GONE);
            mSpecification.setTextColor(getResources().getColor(R.color.lightBlueColor));
            mDescription.setTextColor(getResources().getColor(R.color.darkGreenColor));
            mReviews.setTextColor(getResources().getColor(R.color.darkGreenColor));
        } else if (id == R.id.reviews_btn) {
            tvDescription.setVisibility(View.GONE);
            tvSpecifications.setVisibility(View.GONE);
            recyclerViewItemReviews.setVisibility(View.VISIBLE);
            mReviews.setTextColor(getResources().getColor(R.color.lightBlueColor));
            mDescription.setTextColor(getResources().getColor(R.color.darkGreenColor));
            mSpecification.setTextColor(getResources().getColor(R.color.darkGreenColor));
        } else if (id == R.id.add_to_cart_item_details) {
//            Drawable drawable = holder.mFilteProduct.getDrawable();
//            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            productName = tvTitle.getText().toString();
            storeName = tvStoreName.getText().toString();
            price = tvPrice.getText().toString();
            quantity = tvQuantity.getText().toString();
            // strTotalPrice = price;
            totalPrice = Float.parseFloat(price);
            intQuantity = Integer.parseInt(quantity);
            unitPrice = totalPrice * intQuantity;
            saveToInternalStorage(bitmap);
            cartViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(CartViewModel.class);
            Cart cart = new Cart(productId, imagePath, productName, storeName, totalPrice, unitPrice, intQuantity);
            UpdateCartQuantity updateCartQuantity = new UpdateCartQuantity(productId, intQuantity+1, unitPrice);
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
                Toast.makeText(getContext(), "Cart insert Successfully", Toast.LENGTH_SHORT).show();
            } else {

                for (int i = 0; i < p_id_list.size(); i++) {
                    if (p_id_list.get(i) == productId) {
                        id = p_id_list.get(i);
                    }
                }
                if (id == productId){
                    cartViewModel.updateCartQuantity(updateCartQuantity);
                    Toast.makeText(getContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    cartViewModel.insertCart(cart);
                    Toast.makeText(getContext(), "Cart insert Successfully", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (id == R.id.iv_back) {
            if (status == "1") {
                prefrences.setHomeFragStatus(0);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "Home").addToBackStack(null).commit();
            } else if (status == "2") {
                prefrences.setCategoryFragStatus(1);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CategoryItemsFragment()).addToBackStack(null).commit();
            } else {
                prefrences.setMoreStoresFragStatus(2);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SelectedStoreFragment()).addToBackStack(null).commit();

            }
        }
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
