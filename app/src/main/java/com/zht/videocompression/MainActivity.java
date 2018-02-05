package com.zht.videocompression;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zht.videopick.VideoConfig;
import com.zht.videopick.VideoListActivity;

import java.io.File;
import java.util.ArrayList;

import static com.zht.videopick.VideoConfig.VIDEO_LIST;

public class MainActivity extends AppCompatActivity {

    private EditText videoPath;
    private EditText savePath;


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


    public void pickVideo(View view) {
        startActivityForResult(new Intent(this, VideoListActivity.class), VideoConfig.PICK_VIDEO_REQUEST);
    }


    public void startCompress(View view) {

        String videoUrl = videoPath.getText().toString().trim();
        String saveUrl = savePath.getText().toString().trim();
        String time = CompressUtil.doCompress(this, videoUrl, saveUrl);

        File file = new File(saveUrl);
        long length = file.length();
        Toast.makeText(this, "压缩后文件大小"+length/1024/1024+"M", Toast.LENGTH_SHORT).show();


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
