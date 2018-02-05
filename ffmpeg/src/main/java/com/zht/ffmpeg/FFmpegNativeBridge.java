package com.zht.ffmpeg;

/**
 * 作者：zhanghaitao on 2018/2/5 10:27
 * 邮箱：820159571@qq.com
 *
 * @describe:
 * FFmpeg native 层的 bridge
 */

public class FFmpegNativeBridge {

    static {
        System.loadLibrary("ffmpeg-lib");
    }

    public static int runCommand(String[] command){
        int ret;
        synchronized (FFmpegNativeBridge.class){
            // 不允许多线程访问
            ret = innerRunCommand(command);
        }
        return ret;
    }


    /**
     * 设置是否处于调试状态
     * @param debug
     */
    public static native void setDebug(boolean debug);

    /**
     * 执行指令
     * @param command
     * @return 命令返回结果
     */
    private static native int innerRunCommand(String[] command);


}
