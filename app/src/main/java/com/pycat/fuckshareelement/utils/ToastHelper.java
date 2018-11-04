package com.pycat.fuckshareelement.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pycat.fuckshareelement.R;

public class ToastHelper {

    private ToastHelper() {
    }

    /**
     * 直接使用，不需要使用 builder
     *
     * @param context  context
     * @param text     text
     * @param duration duration
     */
    public static void show(Context context, CharSequence text, int duration) {
        Toast toast = new Toast(context);
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.custom_toast_layout, null);
        TextView tv = v.findViewById(R.id.tv_toast_text);
        tv.setText(text);
        toast.setView(v);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(duration);
        toast.show();
    }

    /**
     * 直接使用，不需要使用 builder
     *
     * @param context  context
     * @param text     text
     * @param duration duration
     */
    public static void show(Context context, @StringRes int text, int duration) {
        Toast toast = new Toast(context);
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.custom_toast_layout, null);
        TextView tv = v.findViewById(R.id.tv_toast_text);
        tv.setText(text);
        toast.setView(v);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static class Builder {

        private final View wrapperLayout;

        private final Toast toast;
        public Builder(Context context) {
            LayoutInflater inflate = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            wrapperLayout = inflate.inflate(R.layout.custom_toast_layout, null);
            toast = new Toast(context);
            toast.setView(wrapperLayout);
        }

        public Builder setWrapperBackgroundColor(@ColorInt int color) {
            wrapperLayout.setBackgroundColor(color);
            return this;
        }

        public Builder setWrapperBackground(Drawable background) {
            wrapperLayout.setBackground(background);
            return this;
        }

        public Builder setWrapperBackgroundResource(@DrawableRes int resid) {
            wrapperLayout.setBackgroundResource(resid);
            return this;
        }

        public Builder setTextColor(@ColorInt int color) {
            TextView tv = wrapperLayout.findViewById(R.id.tv_toast_text);
            tv.setTextColor(color);
            return this;
        }

        public Builder setTextSize(float size) {
            TextView tv = wrapperLayout.findViewById(R.id.tv_toast_text);
            tv.setTextSize(size);
            return this;
        }

        public Builder setTextSize(int unit, float size) {
            TextView tv = wrapperLayout.findViewById(R.id.tv_toast_text);
            tv.setTextSize(unit, size);
            return this;
        }

        public Builder setText(@StringRes int text) {
            TextView tv = wrapperLayout.findViewById(R.id.tv_toast_text);
            tv.setText(text);
            return this;
        }

        public Builder setText(CharSequence text) {
            TextView tv = wrapperLayout.findViewById(R.id.tv_toast_text);
            tv.setText(text);
            return this;
        }

        public Builder setIcon(@DrawableRes int resId) {
            ImageView image = wrapperLayout.findViewById(R.id.img_toast_img);
            image.setImageResource(resId);
            return this;
        }

        @NonNull
        public Context getContext() {
            return this.wrapperLayout.getContext();
        }

        public Toast build() {
            return toast;
        }

        public Toast show(@StringRes int text, int duration) {
            Toast toast = build();
            TextView tv = wrapperLayout.findViewById(R.id.tv_toast_text);
            tv.setText(text);
//            toast.setView(wrapperLayout);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return toast;
        }

        public Toast show(CharSequence text, int duration) {
            Toast toast = build();
            TextView tv = wrapperLayout.findViewById(R.id.tv_toast_text);
            tv.setText(text);
//            toast.setView(wrapperLayout);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return toast;
        }
    }
}
