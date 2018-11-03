package com.pycat.fuckshareelement.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); // 01.开启转场动画
        setTitle(getClass().getSimpleName());
    }

    protected AppCompatActivity get() {
        return this;
    }
}

/**
 * <pre>
 *
 * //退出时使用
 * getWindow().setExitTransition(slide);
 * //第一次进入时使用
 * getWindow().setEnterTransition(slide);
 * //再次进入时使用
 * getWindow().setReenterTransition(slide);
 * 作者：huachao1001
 * 链接：https://www.jianshu.com/p/37e94f8b6f59
 * 來源：简书
 * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
 * </pre>
 */
