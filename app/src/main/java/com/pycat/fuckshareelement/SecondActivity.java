package com.pycat.fuckshareelement;

import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //getWindow().setExitTransition(new Explode()); // 无效，还是被 onCreate 里面的效果钳制！
    }
}
