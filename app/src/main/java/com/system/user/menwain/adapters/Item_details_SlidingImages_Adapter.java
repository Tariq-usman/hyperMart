package com.system.user.menwain.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.system.user.menwain.R;

import androidx.viewpager.widget.PagerAdapter;

public class Item_details_SlidingImages_Adapter extends PagerAdapter {


    private int[] IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public Item_details_SlidingImages_Adapter(Context context, int[] IMAGES) {
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
        return IMAGES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = LayoutInflater.from(context).inflate(R.layout.slidingimages_layout, view, false);
               // inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView =  imageLayout.findViewById(R.id.image);
        imageView.setImageResource(IMAGES[position]);

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
