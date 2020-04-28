package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.system.user.menwain.R;
import com.system.user.menwain.activities.MapsActivity;
import com.system.user.menwain.fragments.cart.AvailNotAvailItemsListsFragment;
import com.system.user.menwain.fragments.cart.PaymentFragment;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.responses.cart.UserAddressListResponse;
import com.system.user.menwain.responses.cart.UserCardsResponse;

import java.util.List;

public class UserCardsAdapter extends RecyclerView.Adapter<UserCardsAdapter.CardViewHolder> {

    List<UserCardsResponse.Cards.Datum> card_list;
    Context context;
    private String sub_string, card_num;

    public UserCardsAdapter(Context applicationContext, List<UserCardsResponse.Cards.Datum> card_list) {
        this.card_list = card_list;
        this.context = applicationContext;
        //texts = new String[address.length];
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_card, parent, false);
        CardViewHolder deliveryAddressViewHolder = new CardViewHolder(view);
        return deliveryAddressViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, final int position) {


        holder.tvCardName.setText(card_list.get(position).getIssuingCompany());
        card_num = card_list.get(position).getCardNumber();

        if (card_num.length() > 4) {
            sub_string = card_num.substring(0, 4);
        }
        holder.tvCardNum.setText(sub_string + " **** **** ****");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment fragment = new PaymentFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return card_list.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCardName, tvCardNum;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardName = itemView.findViewById(R.id.tv_card_name);
            tvCardNum = itemView.findViewById(R.id.tv_card_num);

        }
    }
}
