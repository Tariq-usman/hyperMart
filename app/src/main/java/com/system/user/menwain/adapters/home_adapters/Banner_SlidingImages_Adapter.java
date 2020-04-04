package com.system.user.menwain.adapters.home_adapters;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.HomeBannerResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class Banner_SlidingImages_Adapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    List<HomeBannerResponse.Datum> IMAGES;


    public Banner_SlidingImages_Adapter(Context context, List<HomeBannerResponse.Datum> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = LayoutInflater.from(context).inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView =  imageLayout.findViewById(R.id.image);
        Glide.with(context).load(IMAGES.get(position).getPic()).into(imageView);
//        imageView.setImageResource(IMAGES.get(position));

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
