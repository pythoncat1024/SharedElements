package com.pycat.fuckshareelement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.transition.Explode;
import android.view.View;

public class MainActivity extends BaseActivity {

    private View tvFirst;
    private View tvSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());

        tvFirst = findViewById(R.id.tv_first);
        tvSecond = findViewById(R.id.tv_second);
        ViewCompat.setTransitionName(tvFirst, "tv—first");
        ViewCompat.setTransitionName(tvSecond, "tv—second");
        findViewById(R.id.img_first)
                .setOnClickListener((view) -> {
                    startSecondActivity();
                });

    }

    private void startSecondActivity() {
        Pair[] pairs = {
                new Pair<>(tvFirst, ViewCompat.getTransitionName(tvFirst)),
                new Pair<>(tvSecond, ViewCompat.getTransitionName(tvSecond)),
        };
        Intent intent = new Intent(get(), SecondActivity.class);
        @SuppressWarnings("unchecked")
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, pairs);
        Bundle bundle = optionsCompat.toBundle();
//        startActivity(intent, bundle);
        ActivityCompat.startActivity(get(), intent, bundle);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }
}
