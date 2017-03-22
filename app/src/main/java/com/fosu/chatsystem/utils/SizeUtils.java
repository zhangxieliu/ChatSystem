package com.fosu.chatsystem.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/3/11.
 */

public class SizeUtils {
    public static int convertDpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
