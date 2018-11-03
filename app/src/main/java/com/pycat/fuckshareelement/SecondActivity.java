package com.pycat.fuckshareelement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;

import com.pycat.fuckshareelement.base.BaseKey;
import com.pycat.fuckshareelement.base.GlideApp;

public class SecondActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());

        Intent fromMain = getIntent();
        String imgUrl = fromMain.getStringExtra(BaseKey.KEY_SINGLE_URL);

        ImageView img = findViewById(R.id.second_big_img);
        GlideApp.with(get()).load(imgUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(img);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //getWindow().setExitTransition(new Explode()); // 无效，还是被 onCreate 里面的效果钳制！
    }
}
