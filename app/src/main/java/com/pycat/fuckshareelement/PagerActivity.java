package com.pycat.fuckshareelement;

import android.app.SharedElementCallback;
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

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pycat.fuckshareelement.base.BaseActivity;
import com.pycat.fuckshareelement.base.BaseKey;
import com.pycat.fuckshareelement.base.GlideApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private int initPosition; // 从 recyclerView 过来的时候，传递过来的 pos
    private int mCurrentPos; // 当前 pos
    private Adapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        Intent fromMain = getIntent();
        initPosition = fromMain.getIntExtra(BaseKey.KEY_CURRENT_POSITION, 0);
        mCurrentPos = initPosition;
        String keyMultiUrlSet = BaseKey.KEY_MULTI_URL_SET;
        ArrayList<String> urlList = fromMain.getStringArrayListExtra(keyMultiUrlSet);

        mViewPager = findViewById(R.id.pager_view_pager);
        mPagerAdapter = new Adapter(urlList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(initPosition);
        mViewPager.addOnPageChangeListener(this);
        supportPostponeEnterTransition(); // 先不要加载转场动画
    }

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mCurrentPos != initPosition) {
            Intent intent = new Intent();
            Intent data = intent.putExtra(BaseKey.KEY_CURRENT_POSITION, mCurrentPos);
            setResult(RESULT_OK, data);
            supportPostponeEnterTransition(); // 暂停转场-a
        } else {
            setResult(RESULT_OK);
        }

        finishAfterTransition();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        mCurrentPos = position;
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                // 通过这个方法重置 共享元素
                LogUtils.w(names);
                LogUtils.d(sharedElements);
                names.clear();
                sharedElements.clear();
                // 获取指定 position 对应的 item 的  xml 根布局 view
                Object object = mPagerAdapter.instantiateItem(mViewPager, mCurrentPos);
                LogUtils.v(object);
                if (object instanceof ViewGroup) {
                    ViewGroup itemRoot = (ViewGroup) object;
                    View itemImg = itemRoot.findViewById(R.id.item_pager_image);
                    String imgUrl = mPagerAdapter.mUrls.get(mCurrentPos);
                    names.add(imgUrl);
                    sharedElements.put(names.get(0), itemImg);
//                    LogUtils.e(names); // ok
//                    LogUtils.e(sharedElements); // ok

                } else {
                    throw new RuntimeException("把 item xml 根布局换成 view group.");
                }
            }
        });
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
