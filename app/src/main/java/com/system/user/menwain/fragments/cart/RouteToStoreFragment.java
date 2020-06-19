package com.system.user.menwain.fragments.cart;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.home.HomeFragment;
import com.system.user.menwain.others.Preferences;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RouteToStoreFragment extends Fragment {

    private ImageView mCart, mFavourite, mHome, mCategory, mMore;
    private TextView tvOrderNo, goHome, tvHome, tvCategory, tvCart, tvMore, tvFavourite;
    private CircleImageView circleImageView;
    private TextView tvStoreName, tvTotalPrice, tvLocation, tvRouteMe, tvHomeRoute;
    private Preferences preferences;
    private Geocoder geocoder;
    private List<Address> addresses;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_to_store, container, false);

        preferences = new Preferences(getContext());
        geocoder = new Geocoder(getContext(), Locale.ENGLISH);
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

        circleImageView = view.findViewById(R.id.iv_store_image_route);
        Glide.with(circleImageView.getContext()).load(preferences.getStoreImage()).into(circleImageView);
        tvStoreName = view.findViewById(R.id.tv_store_name_route);
        tvStoreName.setText(preferences.getStoreName());
        tvTotalPrice = view.findViewById(R.id.tv_total_price_route);
        tvTotalPrice.setText("SAR " + preferences.getTotalAmount());
        tvLocation = view.findViewById(R.id.tv_location_route);
        String store_location = preferences.getStoreLocation();
        String[] split_loc = store_location.split(" ");
        double store_lat = Double.parseDouble(split_loc[0]);
        double store_long = Double.parseDouble(split_loc[1]);
        try {
            addresses = geocoder.getFromLocation(store_lat, store_long, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            tvLocation.setText(city);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tvRouteMe = view.findViewById(R.id.tv_route_me);
        tvRouteMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = tvLocation.getText().toString().trim();
//                String geoUri = "http://maps.google.com/maps?q=loc:" + detailsResponse.getData().getStore().getLatitude() + "," + detailsResponse.getData().getStore().getLongitude();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + "&daddr=" + address));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        tvHomeRoute = view.findViewById(R.id.tv_go_to_home);
        tvHomeRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHome.setImageResource(R.drawable.ic_houseblue);
                tvHome.setTextColor(Color.parseColor("#00c1bd"));
                mCategory.setImageResource(R.drawable.ic_searchwhite);
                tvCategory.setTextColor(Color.parseColor("#004040"));
                mFavourite.setImageResource(R.drawable.ic_likewhite);
                tvFavourite.setTextColor(Color.parseColor("#004040"));
                mCart.setImageResource(R.drawable.ic_cart_white);
                tvCart.setTextColor(Color.parseColor("#004040"));
                mMore.setImageResource(R.drawable.ic_morewhite);
                tvMore.setTextColor(Color.parseColor("#004040"));
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment())
                        .addToBackStack(null).commit();
            }
        });
        return view;
    }
}
