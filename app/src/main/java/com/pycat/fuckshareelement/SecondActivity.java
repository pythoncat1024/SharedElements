package com.pycat.fuckshareelement;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.Explode;
import android.view.View;

public class SecondActivity extends BaseActivity {

    private View tvFirst;
    private View tvSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());

        tvFirst = findViewById(R.id.tv_first);
        tvSecond = findViewById(R.id.tv_second);
        ViewCompat.setTransitionName(tvFirst, "tv—first");
        ViewCompat.setTransitionName(tvSecond, "tv—second");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //getWindow().setExitTransition(new Explode()); // 无效，还是被 onCreate 里面的效果钳制！
    }
}
