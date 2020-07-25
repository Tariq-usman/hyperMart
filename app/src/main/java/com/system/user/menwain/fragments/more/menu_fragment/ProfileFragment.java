package com.system.user.menwain.fragments.more.menu_fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.activities.LoginActivity;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.more.MoreFragment;
import com.system.user.menwain.responses.GetUserProfileDetailsResponse;
import com.system.user.menwain.responses.UpdateProfileResponse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    String[] lang = {"English", "عربى"};
    private ImageView ivBackProfile;
    private TextView tvTitleProfile;
    private EditText etFname, etLname, etPhoneNo, etEmail, etCountry, etPassword, etConfPass;
    private RadioButton rbMale, rbFemale;
    private String gender;
    private Button btnRegister;
    private Preferences prefrences;
    private Dialog dialog;
    private boolean profile_status = false;
    private Toast toast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        prefrences = new Preferences(getContext());
        dialog = Utils.dialog(getContext());

        etFname = view.findViewById(R.id.et_f_name_profile);
        etLname = view.findViewById(R.id.et_l_name_profile);
        etPhoneNo = view.findViewById(R.id.et_phone_no_profile);
        etEmail = view.findViewById(R.id.et_email_profile);
        etCountry = view.findViewById(R.id.et_country_profile);
        etPassword = view.findViewById(R.id.et_pass_profile);
        etConfPass = view.findViewById(R.id.et_conf_pass_profile);
        rbMale = view.findViewById(R.id.rb_male_profile);
        rbMale.setOnClickListener(this);
        rbFemale = view.findViewById(R.id.rb_female_profile);
        rbFemale.setOnClickListener(this);
        btnRegister = view.findViewById(R.id.register);
        btnRegister.setOnClickListener(this);


        ivBackProfile = view.findViewById(R.id.iv_back_profile);

        ivBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setProfileFragStatus(0);
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new MoreFragment()).addToBackStack(null).commit();
            }
        });
        getUserProfileDetails();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_male:
                rbMale.setChecked(true);
                rbFemale.setChecked(false);
                break;
            case R.id.rb_female:
                rbFemale.setChecked(true);
                rbMale.setChecked(false);
                break;
            case R.id.register:
                if (profile_status == false) {
                    profile_status = true;

                    etFname.setFocusable(true);
                    etFname.setCursorVisible(true);
                    etFname.setClickable(true);
                    etFname.setFocusableInTouchMode(true);

                    etLname.setFocusable(true);
                    etLname.setCursorVisible(true);
                    etLname.setClickable(true);
                    etLname.setFocusableInTouchMode(true);

                   /* etEmail.setFocusable(true);
                    etEmail.setCursorVisible(true);
                    etEmail.setClickable(true);
                    etEmail.setFocusableInTouchMode(true);*/

                    etCountry.setFocusable(true);
                    etCountry.setCursorVisible(true);
                    etCountry.setClickable(true);
                    etCountry.setFocusableInTouchMode(true);

                    /*etPhoneNo.setFocusable(true);
                    etPhoneNo.setCursorVisible(true);
                    etPhoneNo.setClickable(true);
                    etPhoneNo.setFocusableInTouchMode(true);*/

                    etPassword.setFocusable(true);
                    etPassword.setCursorVisible(true);
                    etPassword.setClickable(true);
                    etPassword.setFocusableInTouchMode(true);

                    etConfPass.setFocusable(true);
                    etConfPass.setCursorVisible(true);
                    etConfPass.setClickable(true);
                    etConfPass.setFocusableInTouchMode(true);
                    btnRegister.setText(getString(R.string.profile_save));
                } else {
                    updateProfileDetails();

                    etFname.setFocusable(false);
                    etFname.setCursorVisible(false);
                    etFname.setClickable(false);
                    etFname.setFocusableInTouchMode(false);

                    etLname.setFocusable(false);
                    etLname.setCursorVisible(false);
                    etLname.setClickable(false);
                    etLname.setFocusableInTouchMode(false);

                   /* etEmail.setFocusable(false);
                    etEmail.setCursorVisible(false);
                    etEmail.setClickable(false);
                    etEmail.setFocusableInTouchMode(false);*/

                    etCountry.setFocusable(false);
                    etCountry.setCursorVisible(false);
                    etCountry.setClickable(false);
                    etCountry.setFocusableInTouchMode(false);

                   /* etPhoneNo.setFocusable(false);
                    etPhoneNo.setCursorVisible(false);
                    etPhoneNo.setClickable(false);
                    etPhoneNo.setFocusableInTouchMode(false);*/

                    etPassword.setFocusable(false);
                    etPassword.setCursorVisible(false);
                    etPassword.setClickable(false);
                    etPassword.setFocusableInTouchMode(false);

                    etConfPass.setFocusable(false);
                    etConfPass.setCursorVisible(false);
                    etConfPass.setClickable(false);
                    etConfPass.setFocusableInTouchMode(false);
                    btnRegister.setText(getString(R.string.profile_edit));
                }
        }

    }

    private void getUserProfileDetails() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_current_user_profile_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    btnRegister.setClickable(true);
                    btnRegister.setEnabled(true);
                    GetUserProfileDetailsResponse userProfileDetailsResponse = gson.fromJson(response, GetUserProfileDetailsResponse.class);
                    etFname.setText(userProfileDetailsResponse.getCustomerdata().getFirstName());
                    etLname.setText(userProfileDetailsResponse.getCustomerdata().getLastName());
                    etPhoneNo.setText(userProfileDetailsResponse.getCustomerdata().getMobile());
                    etEmail.setText(userProfileDetailsResponse.getCustomerdata().getEmail());
                    gender = userProfileDetailsResponse.getCustomerdata().getGender();
                    etCountry.setText(userProfileDetailsResponse.getCustomerdata().getCounty().toString());
                    if (gender.equalsIgnoreCase("Male")) {
                        rbMale.setChecked(true);
                        rbFemale.setChecked(false);
                    } else {
                        rbMale.setChecked(false);
                        rbFemale.setChecked(true);
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null && error.networkResponse != null && error.networkResponse.data != null) {
                    String str = new String(error.networkResponse.data);
                    Log.e("Error : ", str);
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        String strError = jsonObject.getString("message");
                        Toast.makeText(getContext(), "" + strError, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent logInIntnet = new Intent(getContext(), LoginActivity.class);
                                logInIntnet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getActivity().startActivity(logInIntnet);
                                dialog.dismiss();
                            }
                        }, 1000);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }
                } else {
                    toast = Utils.toast(error, getContext());
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
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + prefrences.getToken());
                map.put("Accept", "application/json");
                return map;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void updateProfileDetails() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.update_current_user_profile_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UpdateProfileResponse updateProfileResponse = gson.fromJson(response, UpdateProfileResponse.class);
                Toast.makeText(getContext(), "" + updateProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("profile_error", error.toString());
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
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + prefrences.getToken());
                map.put("Accept", "application/json");
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("first_name", etFname.getText().toString().trim());
                paramsMap.put("last_name", etLname.getText().toString().trim());
//                paramsMap.put("mobile", etPhoneNo.getText().toString().trim());
                paramsMap.put("password", etPassword.getText().toString().trim());
                paramsMap.put("county", etCountry.getText().toString().trim());
                if (rbMale.isChecked()) {
                    paramsMap.put("gender", "male");
                } else {
                    paramsMap.put("gender", "female");
                }
                return paramsMap;
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
}
