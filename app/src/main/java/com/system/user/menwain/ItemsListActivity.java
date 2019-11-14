package com.system.user.menwain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.fragments.AvailableItemsFragment;
import com.system.user.menwain.fragments.DialogFragmentSelectMethod;
import com.system.user.menwain.fragments.NotAvailableItemsFragment;

public class ItemsListActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mAvailable,mNotAvailable,mTitle,mConfirmBtn;
    ImageView mBackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_items_list,new AvailableItemsFragment()).commit();

        mAvailable=findViewById(R.id.available_items);
        mNotAvailable= findViewById(R.id.not_available_items);
        mTitle = findViewById(R.id.title_view);
        mBackBtn = findViewById(R.id.close_back_view);
        mConfirmBtn = findViewById(R.id.confirm_btn_items_list);

        mAvailable.setOnClickListener(this);
        mNotAvailable.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);

        mBackBtn.setImageResource(R.drawable.ic_backwhite);
        mTitle.setText("Items List");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.available_items){
            mNotAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            mAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_selected));
            getSupportFragmentManager().beginTransaction().replace(R.id.container_items_list,new AvailableItemsFragment()).commit();
        }else if (id==R.id.not_available_items){
            mNotAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_selected));
            mAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            getSupportFragmentManager().beginTransaction().replace(R.id.container_items_list,new NotAvailableItemsFragment()).commit();
        }else if (id == R.id.confirm_btn_items_list){
            DialogFragmentSelectMethod dialogFragmentSelectMethod = new DialogFragmentSelectMethod();
            dialogFragmentSelectMethod.show(getSupportFragmentManager(),"Select Method");
        }else if (id == R.id.close_back_view){
            finish();
        }
    }
}
