package com.pycat.fuckshareelement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.apkfuns.logutils.LogUtils;
import com.pycat.fuckshareelement.base.BaseActivity;
import com.pycat.fuckshareelement.base.BaseKey;
import com.pycat.fuckshareelement.base.GlideApp;
import com.pycat.fuckshareelement.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {


    private RecyclerView mRecyclerView;
    private ToggleButton mToggleButton;

    private static final int REQUEST_SINGLE = 1;
    private static final int REQUEST_MULTI = 2;
    private Intent mUpdateBundle; // 更新后的 共享元素
    private List<String> urls;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().setEnterTransition(new Explode());
//        getWindow().setExitTransition(new Explode());
        mRecyclerView = findViewById(R.id.main_recycler_view);
        mLayoutManager = new GridLayoutManager(get(), 3,
                LinearLayout.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        urls = UrlUtils.returnImageUrls();
        MainAdapter adapter = new MainAdapter(urls);
        mRecyclerView.setAdapter(adapter);
        mToggleButton = findViewById(R.id.main_toggle_button);

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                LogUtils.v(names);
                LogUtils.v(sharedElements);
                if (mUpdateBundle != null) {
                    int newPosition = mUpdateBundle
                            .getIntExtra(BaseKey.KEY_CURRENT_POSITION, 0);
                    names.clear();
                    sharedElements.clear();
                    names.add(urls.get(newPosition));
                    // 获取 recyclerView 的 item xml 根布局 view
                    ViewGroup targetV = (ViewGroup) mLayoutManager.findViewByPosition(newPosition);
                    LogUtils.d(targetV);
                    View itemImg = targetV.findViewById(R.id.item_main_img);
                    sharedElements.put(names.get(0), itemImg);
                    supportStartPostponedEnterTransition(); // 启动转场-a
                }
            }
        });

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                LogUtils.w(names);
                LogUtils.w(sharedElements);
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        this.mUpdateBundle = data;
        LogUtils.d("...");
        if(data!=null){
//            supportPostponeEnterTransition(); // 暂停转场-a
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (requestCode == REQUEST_SINGLE) {
                LogUtils.w("return from single");
            } else if (requestCode == REQUEST_MULTI) {
                LogUtils.w("return from multi");
            }
        }
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
                if (mToggleButton.isChecked()) {
                    // start view pager
                    Intent intent = new Intent(get(), PagerActivity.class);
                    intent.putExtra(BaseKey.KEY_CURRENT_POSITION, adapterPosition);
                    intent.putExtra(BaseKey.KEY_MULTI_URL_SET, (ArrayList<String>) mUrls);

                    Pair<View, String> viewStringPair = new Pair<>(vh.itemImg,
                            ViewCompat.getTransitionName(vh.itemImg)
                    );
                    @SuppressWarnings("unchecked")
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(get(), viewStringPair);

                    ActivityCompat.startActivityForResult(get(),
                            intent,
                            REQUEST_MULTI,
                            optionsCompat.toBundle());
                } else {
                    // start single image
                    Intent intent = new Intent(get(), SecondActivity.class);
                    intent.putExtra(BaseKey.KEY_SINGLE_URL, urlStr);

                    Pair<View, String> viewStringPair = new Pair<>(vh.itemImg,
                            ViewCompat.getTransitionName(vh.itemImg)
                    );
                    @SuppressWarnings("unchecked")
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(get(), viewStringPair);
                    ActivityCompat.startActivityForResult(get(),
                            intent,
                            REQUEST_SINGLE,
                            optionsCompat.toBundle());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mUrls.size();
        }
    }
}
