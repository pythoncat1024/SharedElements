package com.pycat.fuckshareelement.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.apkfuns.logutils.LogUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); // 01.开启转场动画
        getWindow().setAllowEnterTransitionOverlap(true); // 官方文档说加
        setTitle(getClass().getSimpleName());
        LogUtils.v(getClass().getSimpleName());
    }

    protected AppCompatActivity get() {
        return this;
    }


    /**
     * 回调时机，当 a->b 通过 startActivityForResult(intent,requestCode,bundle); 调用，
     * 然后 从 b 返回 a 的时候，回调该方法
     * <p>
     * 并且，该方法会在 onActivityResult() 方法之前回调
     *
     * @param resultCode code
     * @param data
     */
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        LogUtils.e(getClass().getSimpleName() + ":onActivityReenter.....");
        LogUtils.v(data);
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
