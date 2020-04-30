package com.system.user.menwain.fragments.more.menu_fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    String[] lang = {"English", "Arabic"};
    private ImageView ivBackProfile;
    private TextView tvTitleProfile;
    private EditText etFname, etLname, etPhoneNo, etEmail, etPassword, etConfPass;
    private RadioButton rbMale, rbFemale;
    private String gender;
    private Button btnRegister;
    private Preferences prefrences;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private boolean profile_status = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        prefrences = new Preferences(getContext());
        customDialog(getContext());

        etFname = view.findViewById(R.id.et_f_name_profile);
        etLname = view.findViewById(R.id.et_l_name_profile);
        etPhoneNo = view.findViewById(R.id.et_phone_no_profile);
        etEmail = view.findViewById(R.id.et_email_profile);
        etPassword = view.findViewById(R.id.et_pass_profile);
        etConfPass = view.findViewById(R.id.et_conf_pass_profile);
        rbMale = view.findViewById(R.id.rb_male_profile);
        rbMale.setOnClickListener(this);
        rbFemale = view.findViewById(R.id.rb_female_profile);
        rbFemale.setOnClickListener(this);
        btnRegister = view.findViewById(R.id.register);
        btnRegister.setOnClickListener(this);
        if (prefrences.getToken().isEmpty()||prefrences.getToken()==null){
            btnRegister.setClickable(false);
            btnRegister.setEnabled(false);
        }else {
            btnRegister.setClickable(true);
            btnRegister.setEnabled(true);

        }


        ivBackProfile = getActivity().findViewById(R.id.iv_back);
        ivBackProfile.setVisibility(View.VISIBLE);

        ivBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setProfileFragStatus(0);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new MoreFragment()).addToBackStack(null).commit();
                ivBackProfile.setVisibility(View.INVISIBLE);
            }
        });
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = view.findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, lang);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

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

                    etEmail.setFocusable(true);
                    etEmail.setCursorVisible(true);
                    etEmail.setClickable(true);
                    etEmail.setFocusableInTouchMode(true);

                    etPhoneNo.setFocusable(true);
                    etPhoneNo.setCursorVisible(true);
                    etPhoneNo.setClickable(true);
                    etPhoneNo.setFocusableInTouchMode(true);

                    etPassword.setFocusable(true);
                    etPassword.setCursorVisible(true);
                    etPassword.setClickable(true);
                    etPassword.setFocusableInTouchMode(true);

                    etConfPass.setFocusable(true);
                    etConfPass.setCursorVisible(true);
                    etConfPass.setClickable(true);
                    etConfPass.setFocusableInTouchMode(true);
                    btnRegister.setText("Save");
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

                    etEmail.setFocusable(false);
                    etEmail.setCursorVisible(false);
                    etEmail.setClickable(false);
                    etEmail.setFocusableInTouchMode(false);

                    etPhoneNo.setFocusable(false);
                    etPhoneNo.setCursorVisible(false);
                    etPhoneNo.setClickable(false);
                    etPhoneNo.setFocusableInTouchMode(false);

                    etPassword.setFocusable(false);
                    etPassword.setCursorVisible(false);
                    etPassword.setClickable(false);
                    etPassword.setFocusableInTouchMode(false);

                    etConfPass.setFocusable(false);
                    etConfPass.setCursorVisible(false);
                    etConfPass.setClickable(false);
                    etConfPass.setFocusableInTouchMode(false);
                    btnRegister.setText("Edit");
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
                GetUserProfileDetailsResponse userProfileDetailsResponse = gson.fromJson(response, GetUserProfileDetailsResponse.class);
                etFname.setText(userProfileDetailsResponse.getCustomerdata().getFirstName());
                etLname.setText(userProfileDetailsResponse.getCustomerdata().getLastName());
                etPhoneNo.setText(userProfileDetailsResponse.getCustomerdata().getMobile());
                etEmail.setText(userProfileDetailsResponse.getCustomerdata().getEmail());
                gender = userProfileDetailsResponse.getCustomerdata().getGender();
                if (gender.equalsIgnoreCase("Male")){
                    rbMale.setChecked(true);
                    rbFemale.setChecked(false);
                }else {
                    rbMale.setChecked(false);
                    rbFemale.setChecked(true);
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "You need to Login First!", Toast.LENGTH_SHORT).show();
                Log.e("profiel_error", error.toString());
                Intent logInIntnet = new Intent(getContext(), LoginActivity.class);
                logInIntnet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(logInIntnet);
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + prefrences.getToken());
                return map;
            }
        };
        requestQueue.add(request);
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
                Log.e("profiel_error", error.toString());
                Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + prefrences.getToken());
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("first_name", etFname.getText().toString().trim());
                paramsMap.put("last_name", etLname.getText().toString().trim());
                paramsMap.put("mobile", etPhoneNo.getText().toString().trim());
                paramsMap.put("password", etPassword.getText().toString().trim());
                paramsMap.put("county", "pakistan");
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

    public void customDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.layout_loading_dialog);
        }
        dialog = builder.create();
    }
}
