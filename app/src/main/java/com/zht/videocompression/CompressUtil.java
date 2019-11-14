package com.zht.videocompression;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.zht.ffmpeg.FFmpegNativeBridge;

/**
 * 作者：zhanghaitao on 2018/2/5 10:41
 * 邮箱：820159571@qq.com
 *
 * @describe:
 */

public class CompressUtil {

    private static final int REQUEST_PERMISSION = 100;

    public static String doCompress(Activity activity,String videoPath,String savePath) {
        //check permission
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION);
        } else {

            long startTime = System.currentTimeMillis();
            int ret = FFmpegNativeBridge.runCommand(new String[]{"ffmpeg",
                    "-i", videoPath,//"/storage/emulated/0/AzRecorderFree/2017_10_13_14_57_59.mp4",
                    "-y",
                    "-c:v", "libx264",
                    "-c:a", "aac",
                    "-vf", "scale=-2:720",
                    "-preset", "ultrafast",
                  //  "-b:v", "450k",
                    "-b:a", "192k",
                    savePath//"/storage/emulated/0/Download/a.mp4"

            });
            System.out.println("ret: " + ret + ", time: " + (System.currentTimeMillis() - startTime));

            return "elapsed time："+(System.currentTimeMillis() - startTime)+"ms";
        }
        return null;
    }




}
