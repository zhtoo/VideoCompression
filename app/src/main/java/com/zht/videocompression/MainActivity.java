package com.zht.videocompression;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zht.ffmpeg.FFmpegNativeBridge;
import com.zht.videopick.VideoConfig;
import com.zht.videopick.VideoListActivity;

import java.io.File;
import java.util.ArrayList;

import static com.zht.videopick.VideoConfig.VIDEO_LIST;

public class MainActivity extends AppCompatActivity {

    private EditText videoPath;
    private EditText savePath;

    private static final int REQUEST_PERMISSION = 100;

    private static final int HANDLER_COMMAND = 101;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLER_COMMAND) {
                if (waitingDialog != null && waitingDialog.isShowing()) {
                    waitingDialog.dismiss();
                    Toast.makeText(MainActivity.this, "压缩后文件大小", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    private ProgressDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoPath = (EditText) findViewById(R.id.main_video_path);
        savePath = (EditText) findViewById(R.id.main_save_path);

        String saveUrl = "/storage/emulated/0/Download/a.mp4";
        savePath.setText(saveUrl);
        checkPermission();

    }
    private void showWaitingDialog() {
        if (waitingDialog == null) {
            waitingDialog = new ProgressDialog(MainActivity.this);
            waitingDialog.setTitle("视频压缩中...");
            waitingDialog.setMessage("请等待中...");
            waitingDialog.setIndeterminate(true);
            waitingDialog.setCancelable(false);
        }
        waitingDialog.show();
    }

    public void pickVideo(View view) {
        startActivityForResult(new Intent(this, VideoListActivity.class), VideoConfig.PICK_VIDEO_REQUEST);
    }

    public void startCompress(View view) {
        final String videoUrl = videoPath.getText().toString().trim();
        final String saveUrl = savePath.getText().toString().trim();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION);
        } else {
            showWaitingDialog();

            new CompressThread(videoUrl, saveUrl,
                    handler, HANDLER_COMMAND).start();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }
        if (data == null) {
            return;
        }
        if (requestCode == VideoConfig.PICK_VIDEO_REQUEST) {
            ArrayList<CharSequence> list = data.getCharSequenceArrayListExtra(VIDEO_LIST);

            if (list != null && list.size() > 0) {
                String s = list.get(0).toString().trim();
                videoPath.setText(s);
            }
        }
    }

    /**
     * 权限检测
     */
    private void checkPermission() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};

        //定义一个变量 记录当前权限的状态
        int checkSlfePermission = PackageManager.PERMISSION_GRANTED;

        for (int i = 0; i < permissions.length; i++) {
            int denide = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (denide == PackageManager.PERMISSION_DENIED) {
                checkSlfePermission = PackageManager.PERMISSION_DENIED;
            }
        }

        //当前么有相应的权限
        if (checkSlfePermission == PackageManager.PERMISSION_DENIED) {
            //申请权限 （弹出一个申请权限的对话框）
            ActivityCompat.requestPermissions(this, permissions, 120);
        } else
            //申请到了权限
            if (checkSlfePermission == PackageManager.PERMISSION_GRANTED) {

            }
    }

}
