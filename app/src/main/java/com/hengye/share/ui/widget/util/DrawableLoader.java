package com.hengye.share.ui.widget.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.hengye.share.util.ResUtil;

/**
 * Created by yuhy on 16/7/26.
 */
public class DrawableLoader {

    public static Drawable setTintDrawable(@DrawableRes int drawableId, @ColorRes int colorId){
        return setTintDrawable(ResUtil.getDrawable(drawableId), ResUtil.getColor(colorId));
    }

    public static Drawable setTintDrawable(Drawable drawable, int color){
//        Drawable drawable1= DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        return drawable;
    }
}
