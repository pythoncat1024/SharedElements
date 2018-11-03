package com.pycat.fuckshareelement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        findViewById(R.id.img_first)
                .setOnClickListener((view) -> {
                    startSecondActivity();
                });
    }

    private void startSecondActivity() {
        Intent intent = new Intent(get(), SecondActivity.class);
        @SuppressWarnings("unchecked")
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this);
        Bundle bundle = optionsCompat.toBundle();
        startActivity(intent, bundle);
    }
}
