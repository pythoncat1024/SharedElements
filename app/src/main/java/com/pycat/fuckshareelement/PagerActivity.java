package com.pycat.fuckshareelement;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pycat.fuckshareelement.base.BaseActivity;
import com.pycat.fuckshareelement.base.BaseKey;
import com.pycat.fuckshareelement.base.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class PagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

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
        mViewPager.addOnPageChangeListener(this);
        supportPostponeEnterTransition(); // 先不要加载转场动画
    }

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(this);
        super.onDestroy();
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position Position index of the first page currently being displayed.
     *                 Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position){

    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see com.android.internal.widget.ViewPager#SCROLL_STATE_IDLE
     * @see com.android.internal.widget.ViewPager#SCROLL_STATE_DRAGGING
     * @see com.android.internal.widget.ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state){

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
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource) {
                            supportStartPostponedEnterTransition(); // 开始加载转场动画
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model,
                                                       Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            // 此时图片加载完成了
                            supportStartPostponedEnterTransition(); // 开始加载转场动画
                            return false;
                        }
                    })
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
