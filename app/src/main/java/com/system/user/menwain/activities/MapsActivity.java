package com.system.user.menwain.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.R;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.cart.AddAddressResponse;
import com.system.user.menwain.utils.URLs;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_REQUEST_INT = 101;
    private GoogleMap mMap;
    private TextView searched_location;
    private EditText searchEt, addressTitleView;
    private ImageView backBtn, searchBtn, locationPinUp, currentLocationBtn;
    private LatLng current, center;
    private Dialog myDialog;
    private Button btnYes, doneBtn;
    double currentLat;
    double currentLng;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Preferences prefrences;
    private ProgressDialog progressDialog;
    private int address_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        prefrences = new Preferences(this);
        customProgressDialog(MapsActivity.this);
        address_id = getIntent().getIntExtra("address_id", 0);

        searchBtn = findViewById(R.id.searchBtn);
        searchEt = findViewById(R.id.searchEt);
        searched_location = findViewById(R.id.searched_location);
        locationPinUp = findViewById(R.id.imgLocationPinUp);
        currentLocationBtn = findViewById(R.id.ivCurrent_location);
        doneBtn = findViewById(R.id.address_done_btn);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        fetchLastLocation();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMap.clear();
                    String location = searchEt.getText().toString();
                    List<Address> addressList = null;

                    if (searchEt.equals("")) {
                        Toast.makeText(getApplicationContext(), "Enter Location:", Toast.LENGTH_LONG).show();
                    } else if (location != null || !location.equals("")) {
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            addressList = geocoder.getFromLocationName(location, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (addressList.size() == 0) {
                            Toast.makeText(getApplicationContext(), "No location found!", Toast.LENGTH_LONG).show();
                        }
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        currentLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_locations_green))));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                    //mMap.animateCamera(CameraUpdateFactory.newLatLng(current));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
                } catch (Exception e) {
                    Log.e("exception ", e.toString());
                }

            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddress();
            }
        });
    }

    LocationManager locationManager;
    LocationListener locationListener;

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    currentLat = location.getLatitude();
                    currentLng = location.getLongitude();
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    View mapView = mapFragment.getView();
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                try {
                    current = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions()
                            .position(current)
                            .title("Current Location"));
                    //.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_locations_green))));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                    //mMap.animateCamera(CameraUpdateFactory.newLatLng(current));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
//                mMap.setMyLocationEnabled(true);

                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                } catch (Exception e) {
                    Log.e("exception", e.toString());
                }
            }

        });
        mMap = googleMap;

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                center = mMap.getCameraPosition().target;
                Log.e("lat,long", center.latitude + " , " + center.longitude + "");
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.ENGLISH);
                List<Address> addresses;

                try {
                    addresses = geocoder.getFromLocation(center.latitude, center.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    if (addresses.isEmpty()) {
                        addresses = geocoder.getFromLocation(33.684422, 73.047882, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        searched_location.setText(address);
                        locationPinUp.setVisibility(View.INVISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentLocationBtn.setVisibility(View.INVISIBLE);
                            }
                        }, 2000);
                    } else {
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        searched_location.setText(address);
                        locationPinUp.setVisibility(View.INVISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentLocationBtn.setVisibility(View.INVISIBLE);
                            }
                        }, 2000);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                locationPinUp.setVisibility(View.VISIBLE);
                currentLocationBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    public void addAddress() {
        myDialog = new Dialog(MapsActivity.this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.custom_dialog_add_edit_address);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addressTitleView = myDialog.findViewById(R.id.et_edit_address);
        btnYes = myDialog.findViewById(R.id.btn_save);
        btnYes.setEnabled(true);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address_id >= 0) {
                    updateUserAddress();
                } else {
                    addUserAddress();
                }
            }
        });
        myDialog.show();
    }

    private void addUserAddress() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.add_user_address_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AddAddressResponse addressResponse = gson.fromJson(response, AddAddressResponse.class);
                Toast.makeText(MapsActivity.this, "" + addressResponse.getMessage(), Toast.LENGTH_SHORT).show();
                myDialog.cancel();
                finish();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Add_error", error.toString());
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
                map.put("latitude", String.valueOf(center.latitude));
                map.put("longitude", String.valueOf(center.longitude));
                if (addressTitleView.getText().toString().trim().isEmpty() || addressTitleView.getText().toString().trim() == null) {
                    map.put("address_line1", searched_location.getText().toString().trim());
                } else {
                    map.put("address_line1", addressTitleView.getText().toString().trim());
                }
                map.put("status", 0 + "");
                return map;
            }
        };
        requestQueue.add(request);
    }

    private void updateUserAddress() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.update_user_address_url + address_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AddAddressResponse addressResponse = gson.fromJson(response, AddAddressResponse.class);
                Toast.makeText(MapsActivity.this, "" + addressResponse.getMessage(), Toast.LENGTH_SHORT).show();
                myDialog.cancel();
                finish();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Add_error", error.toString());
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
                map.put("latitude", String.valueOf(center.latitude));
                map.put("longitude", String.valueOf(center.longitude));
                if (addressTitleView.getText().toString().trim().isEmpty() || addressTitleView.getText().toString().trim() == null) {
                    map.put("address_line1", searched_location.getText().toString().trim());
                } else {
                    map.put("address_line1", addressTitleView.getText().toString().trim());
                }
                map.put("status", 0 + "");
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


    @Override
    public void onBackPressed() {
        finish();
    }
}
