package com.zht.videocompression;

import android.os.Handler;

import com.zht.ffmpeg.FFmpegNativeBridge;

/**
 * Created by ZhangHaitao on 2019/11/14
 */
public class CompressThread extends Thread {

    int HANDLER_COMMAND;
    String videoPath;
    String savePath;
    Handler handler;

    public CompressThread(String videoPath, String savePath,
                          Handler handler,
                          int msgWhat) {
        this.HANDLER_COMMAND = msgWhat;
        this.videoPath = videoPath;
        this.savePath = savePath;
        this.handler = handler;
    }

    @Override
    public void run() {
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
        handler.sendEmptyMessage(HANDLER_COMMAND);
        handler = null;
    }
}
