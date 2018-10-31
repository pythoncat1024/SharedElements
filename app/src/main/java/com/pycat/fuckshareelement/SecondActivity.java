package com.pycat.fuckshareelement;

import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Transition transition =
            TransitionInflater.from(get()).inflateTransition(R.transition.slide);
        getWindow().setEnterTransition(transition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //getWindow().setExitTransition(new Explode()); // 无效，还是被 onCreate 里面的效果钳制！
    }
}
