package com.system.user.menwain.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.home.HomeBannerResponse;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class Item_details_SlidingImages_Adapter extends PagerAdapter {


    private List<HomeBannerResponse.Datum> list;
    private LayoutInflater inflater;
    private Context context;


    public Item_details_SlidingImages_Adapter(Context context, List<HomeBannerResponse.Datum> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = LayoutInflater.from(context).inflate(R.layout.slidingimages_layout, view, false);
               // inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView =  imageLayout.findViewById(R.id.image);
        Glide.with(imageView.getContext()).load(list.get(position).getPic()).into(imageView);
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
