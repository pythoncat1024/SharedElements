package com.pycat.fuckshareelement;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pycat.fuckshareelement.base.BaseActivity;
import com.pycat.fuckshareelement.base.BaseKey;
import com.pycat.fuckshareelement.base.GlideApp;

public class SecondActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
//        getWindow().setEnterTransition(new Explode());
//        getWindow().setExitTransition(new Explode());

        Intent fromMain = getIntent();
        String imgUrl = fromMain.getStringExtra(BaseKey.KEY_SINGLE_URL);

        ImageView img = findViewById(R.id.second_big_img);

        ViewCompat.setTransitionName(img, imgUrl);

        supportPostponeEnterTransition(); // 不要加载转场动画
        GlideApp.with(get()).load(imgUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {

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
                .into(img);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //getWindow().setExitTransition(new Explode()); // 无效，还是被 onCreate 里面的效果钳制！
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        LogUtils.d("...");
    }

}
