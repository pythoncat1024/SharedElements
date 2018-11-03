package com.pycat.fuckshareelement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.apkfuns.logutils.LogUtils;
import com.pycat.fuckshareelement.base.BaseKey;
import com.pycat.fuckshareelement.base.GlideApp;
import com.pycat.fuckshareelement.utils.UrlUtils;

import java.util.List;

public class MainActivity extends BaseActivity {


    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().setEnterTransition(new Explode());
//        getWindow().setExitTransition(new Explode());
        mRecyclerView = findViewById(R.id.main_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(get(), 3,
                LinearLayout.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        List<String> urls = UrlUtils.returnImageUrls();
        MainAdapter adapter = new MainAdapter(urls);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }


    class VH extends RecyclerView.ViewHolder {

        private final ImageView itemImg;

        public VH(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.item_main_img);
        }
    }

    class MainAdapter extends RecyclerView.Adapter<VH> {

        private final List<String> mUrls;

        public MainAdapter(List<String> urls) {
            this.mUrls = urls;
            LogUtils.d(mUrls);
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(get())
                    .inflate(R.layout.item_main_layout, viewGroup, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VH vh, int position) {
            int adapterPosition = vh.getAdapterPosition();
            String urlStr = mUrls.get(adapterPosition);
            GlideApp.with(get())
                    .load(urlStr)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .centerCrop()
                    .into(vh.itemImg);
            ViewCompat.setTransitionName(vh.itemImg, urlStr);

            vh.itemImg.setOnClickListener(v -> {
                Intent intent = new Intent(get(), SecondActivity.class);
                intent.putExtra(BaseKey.KEY_SINGLE_URL, urlStr);

                Pair<View, String> viewStringPair = new Pair<>(vh.itemImg,
                        ViewCompat.getTransitionName(vh.itemImg)
                );
                @SuppressWarnings("unchecked")
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(get(), viewStringPair);

                ActivityCompat.startActivity(get(), intent, optionsCompat.toBundle());
            });
        }

        @Override
        public int getItemCount() {
            return mUrls.size();
        }
    }
}
