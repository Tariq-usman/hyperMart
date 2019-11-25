package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.ItemsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SelectedItemAdapter extends RecyclerView.Adapter<SelectedItemAdapter.SelectedItemViewHolder> {
    private String [] productsName;
Context context;
private int [] items;
    public SelectedItemAdapter(Context context, String[] productsName, int[] items) {
        this.productsName = productsName;
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_items,parent,false);
        SelectedItemViewHolder categoryViewHolder = new SelectedItemViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemViewHolder holder, int position) {
        holder.mProductNameView.setText(productsName[position]);
        holder.mProduct.setImageResource(items[position]);
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                             FragmentManager fragmentManager =   ((AppCompatActivity)context).getSupportFragmentManager();
                             fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new ItemsFragment()).addToBackStack(null).commit();
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new ItemsFragment();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class SelectedItemViewHolder extends RecyclerView.ViewHolder{
        TextView mProductNameView;
        CircleImageView mProduct;
        public SelectedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mProduct = itemView.findViewById(R.id.selected_product_view);
            mProductNameView=itemView.findViewById(R.id.product_name_view);
        }
    }
}
