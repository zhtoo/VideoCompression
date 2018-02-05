package com.zht.videopick;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Video.Thumbnails;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.zht.videopick.VideoConfig.VIDEO_LIST;


/**
 * 作者：zhanghaitao on 2018/1/12 09:28
 * 邮箱：820159571@qq.com
 *
 * @describe:
 */

public class VideoListActivity extends AppCompatActivity {

    public VideoListActivity instance = null;


    List<Video> listVideos;
    int videoSize;
    private RecyclerView mRecycler;
    private VideoRecyclerAdapter mAdapter;

    private Map<Integer, Video> selectVideos;
    private ArrayList<CharSequence> listVideoPath = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        selectVideos = new LinkedHashMap<>();

        LinearLayout mContainer = (LinearLayout) findViewById(R.id.video_container);
        instance = this;
        AbstructProvider provider = new VideoProvider(instance);
        listVideos = provider.getList();

        videoSize = listVideos.size();

        initRecycler();
        loadImages();
    }



    /**
     * 将选择的数据传递回去
     */
    private void startUploadVideoActivity() {
        Intent intent = new Intent();
       // intent.setClass(VideoListActivity.this, clazz);

        for (Integer video : selectVideos.keySet()) {
            String path = selectVideos.get(video).getPath();
            listVideoPath.add(path);
        }
        intent.putCharSequenceArrayListExtra(VIDEO_LIST, listVideoPath);
        setResult(VideoConfig.PICK_VIDEO_REQUEST, intent);
        finish();
    }

    private void initRecycler() {
        mRecycler = (RecyclerView) findViewById(R.id.video_recycler);
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        lm.setOrientation(GridLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(lm);
        mAdapter = new VideoRecyclerAdapter(this, listVideos);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new VideoRecyclerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position, boolean isSelect) {
                if (isSelect) {
                    selectVideos.put(position, listVideos.get(position));
                } else {
                    selectVideos.remove(position);
                }

            }
        });
    }

    /**
     * 加载视频预览图片
     */
    private void loadImages() {
        final Object data = getLastNonConfigurationInstance();
        if (data == null) {
            new LoadImagesFromSDCard().execute();
        } else {
            final LoadedImage[] photos = (LoadedImage[]) data;
            if (photos.length == 0) {
                new LoadImagesFromSDCard().execute();
            }
            for (LoadedImage photo : photos) {
                addImage(photo);
            }
        }
    }


    public void  returnClass(View view){
       this.finish();
    }

    /**
     *
     * @param view
     */
    public void  pick(View view){
        startUploadVideoActivity();
    }

    //添加图片
    private void addImage(LoadedImage... value) {
        for (LoadedImage image : value) {
            mAdapter.addPhoto(image);
            mAdapter.notifyDataSetChanged();
        }
    }

    class LoadImagesFromSDCard extends AsyncTask<Object, LoadedImage, Object> {
        @Override
        protected Object doInBackground(Object... params) {
            Bitmap bitmap = null;
            for (int i = 0; i < videoSize; i++) {
                bitmap = getVideoThumbnail(listVideos.get(i).getPath(), 480, 270, Thumbnails.MINI_KIND);
                if (bitmap != null) {
                    publishProgress(new LoadedImage(bitmap));
                }
            }
            return null;
        }

        /**
         * 获取视频缩略图
         */
        private Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
           long mStartTime = System.currentTimeMillis();
            Bitmap bitmap = null;
            bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            long mEndTime = System.currentTimeMillis();
            Log.e("获取视频耗时",""+(mEndTime-mStartTime ));
            return bitmap;
        }

        @Override
        public void onProgressUpdate(LoadedImage... value) {
            addImage(value);
        }

    }
}