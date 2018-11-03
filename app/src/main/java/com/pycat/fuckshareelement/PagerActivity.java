package com.pycat.fuckshareelement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pycat.fuckshareelement.base.BaseActivity;
import com.pycat.fuckshareelement.base.BaseKey;
import com.pycat.fuckshareelement.base.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class PagerActivity extends BaseActivity {

    private ViewPager mViewPager;
    private int initPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        Intent fromMain = getIntent();
        initPosition = fromMain.getIntExtra(BaseKey.KEY_CURRENT_POSITION, 0);
        String keyMultiUrlSet = BaseKey.KEY_MULTI_URL_SET;
        ArrayList<String> urlList = fromMain.getStringArrayListExtra(keyMultiUrlSet);

        mViewPager = findViewById(R.id.pager_view_pager);
        mViewPager.setAdapter(new Adapter(urlList));
        mViewPager.setCurrentItem(initPosition);
    }

    class Adapter extends PagerAdapter {

        private final List<String> mUrls;

        public Adapter(List<String> urls) {
            this.mUrls = urls;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            String imgUrl = mUrls.get(position);
            View view = LayoutInflater.from(get())
                    .inflate(R.layout.item_pager_layout, container, false);
            ImageView pagerImg = view.findViewById(R.id.item_pager_image);

            if (position == initPosition) {
                ViewCompat.setTransitionName(pagerImg, imgUrl);
            }

            GlideApp.with(get())
                    .load(imgUrl)
                    .error(R.mipmap.ic_launcher_round)
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(pagerImg);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container,
                                int position,
                                @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mUrls.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }
    }
}
