package com.zht.videopick;

import android.graphics.Bitmap;

/**
 * 作者：zhanghaitao on 2018/1/12 09:29
 * 邮箱：820159571@qq.com
 *
 * @describe:存储视频的第一帧图片的bitmap
 */

public class LoadedImage {
    Bitmap mBitmap;

    public LoadedImage(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }


}
