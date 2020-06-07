package com.system.user.menwain.fragments.cart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.LoginActivity;
import com.system.user.menwain.adapters.cart_adapters.CartItemsAdapter;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class CartFragment extends Fragment implements View.OnClickListener {

    private EditText etSearch;
    private CartViewModel cartViewModel;
    RecyclerView recyclerViewCartItems;
    CartItemsAdapter cartItemsAdapter;
    TextView mProceedBtn, tvTotalCartAmount;
    private float totalCartAmount;
    String user_token;
    Context context;
    private CardView mSearchViewCart;
    private SharedPreferences.Editor editor;
    SharedPreferences preferences, fragment_status_pref;
    private Preferences prefrences;
    private List<Integer> cartList = new ArrayList<Integer>();
    private List<Cart> searchlist = new ArrayList<Cart>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        editor = getActivity().getSharedPreferences("fragment_status", Context.MODE_PRIVATE).edit();
        prefrences = new Preferences(getContext());
        user_token = prefrences.getToken();
        recyclerViewCartItems = view.findViewById(R.id.recycler_view_cart_items);
        recyclerViewCartItems.setHasFixedSize(true);
        recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(getContext()));

        mProceedBtn = view.findViewById(R.id.proceed_btn);
        mProceedBtn.setOnClickListener(this);
        etSearch = view.findViewById(R.id.et_search_cart);
        etSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white, 0, 0, 0);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    etSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    //Assign your image again to the view, otherwise it will always be gone even if the text is 0 again.
                    etSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white, 0, 0, 0);
                }
                final String query = s.toString().toLowerCase();
                searchlist.clear();
                cartViewModel.getCartDataList().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
                    @Override
                    public void onChanged(List<Cart> carts) {
                        for (int i = 0; i < carts.size(); i++) {
                            final String text = carts.get(i).getProduct_name().toLowerCase();
                            if (text.contains(query)) {
                                searchlist.add(carts.get(i));
                            }
                        }
                        cartItemsAdapter.setCartData(searchlist, cartViewModel);
                        cartItemsAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearch.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        cartViewModel = ViewModelProviders.of(CartFragment.this).get(CartViewModel.class);

        tvTotalCartAmount = view.findViewById(R.id.tv_total_cart_amount);
        cartViewModel.getTotalCartPrice().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {

                if (aFloat != null) {
                    tvTotalCartAmount.setText(aFloat + "");
                } else {
                    tvTotalCartAmount.setText(00.0 + "");
                }
            }
        });
        cartViewModel.getCartDataList().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                for (int i = 0; i < carts.size(); i++) {
                    cartList.add(carts.get(i).getP_id());
                }
            }
        });

        getCartData();
        return view;
    }

    private void getCartData() {
        cartItemsAdapter = new CartItemsAdapter();
        recyclerViewCartItems.setAdapter(cartItemsAdapter);
        cartViewModel.getCartDataList().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                cartItemsAdapter.setCartData(carts, cartViewModel);
                cartItemsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        final DeliveryAddressFragment fragment = new DeliveryAddressFragment();
        if (id == R.id.proceed_btn) {
            cartViewModel.getCartDataList().observe(CartFragment.this, new Observer<List<Cart>>() {
                @Override
                public void onChanged(List<Cart> carts) {
                    if (user_token.isEmpty()) {
                        Intent logInIntnet = new Intent(getContext(), LoginActivity.class);
                        logInIntnet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getActivity().startActivity(logInIntnet);
                    } else if (carts.size() == 0) {
                        Toast.makeText(getContext(), getContext().getString(R.string.cart_is_empty), Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < carts.size(); i++) {
                            cartList.add(carts.get(i).getP_id());
                        }
                        prefrences.setCartFragStatus(1);
                        prefrences.setOrderStatus(1);
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
                    }

                }
            });

        }
    }
}
