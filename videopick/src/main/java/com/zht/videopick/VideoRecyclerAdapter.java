package com.zht.videopick;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * 作者：zhanghaitao on 2018/1/12 09:30
 * 邮箱：820159571@qq.com
 *
 * @describe:列表的Adapter
 */

public class VideoRecyclerAdapter extends RecyclerView.Adapter {

    List<Video> listVideos;
    int local_postion = 0;
    boolean imageChage = false;
    private ArrayList<LoadedImage> photos = new ArrayList<LoadedImage>();
    private LayoutInflater mLayoutInflater;
    private Context context;

    public VideoRecyclerAdapter(Context context, List<Video> listVideos) {
        mLayoutInflater = LayoutInflater.from(context);
        this.listVideos = listVideos;
        this.context = context;
    }

    public void addPhoto(LoadedImage image) {
        photos.add(image);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(mLayoutInflater.inflate(R.layout.item_video_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoViewHolder viewHolder = (VideoViewHolder) holder;
        viewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


    private class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private ImageView mSelectImage;
        private TextView mTime;
        private TextView mSize;
        private TextView mName;
        private LinearLayout mItem;
        private int scaleSize;
        private int space;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        private boolean isSelect;


        public VideoViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.video_image);
            mSelectImage = (ImageView) itemView.findViewById(R.id.video_select);
            mTime = (TextView) itemView.findViewById(R.id.video_time);
            mSize = (TextView) itemView.findViewById(R.id.video_size);
            mName = (TextView) itemView.findViewById(R.id.video_name);

            mItem = (LinearLayout) itemView.findViewById(R.id.video_item);
            int screenWidth = getWidthPixels();
            space = dp2px(6);
            scaleSize = (screenWidth - (2 + 1) * space) / 2;

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mImage.getLayoutParams();
            layoutParams.width = scaleSize;
            layoutParams.height = scaleSize * 9 / 16;

            setSelect(false);
        }


        public void setData(final int position) {

            if (position % 2 == 0) {
                mItem.setPadding(space, 0, space / 2, 0);
            } else {
                mItem.setPadding(space / 2, 0, space, 0);
            }

            Video video = listVideos.get(position);
            mImage.setImageBitmap(photos.get(position).getBitmap());
            mTime.setText(getTimeString(video.getDuration()));
            mSize.setText((video.getSize() / 1024 / 1024) + "M");
            mName.setText(video.getDisplayName());

            mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeSelect();
                    if (listener != null) {
                        listener.onItemClick(position,isSelect);
                    }
                }
            });


        }


        private void changeSelect() {
            if (isSelect) {
                mSelectImage.setImageResource(R.drawable.ic_pick_video_un_select);
            } else {
                mSelectImage.setImageResource(R.drawable.ic_pick_video_select);
            }
            setSelect(!isSelect);
        }


    }

    private String getTimes(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+:08:00"));
        String hms = formatter.format(time);
        return hms;
    }

    public onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public interface onItemClickListener {
        void onItemClick(int position, boolean isSelect);
    }

    private String getTimeString(long time) {
        long day = time / 1000 / 60 / 60 / 24;
        long hours = time / 1000 / 60 / 60;
        long min = time / 1000 / 60 % 60;
        long sec = time / 1000 % 60;
        String hms = new String();
        if (day > 0) {
            hms += day + "天  ";
        }
        if (hours < 10) {
            hms += "0";
        }
        hms += (hours + ":");
        if (min < 10) {
            hms += "0";
        }
        hms += (min + ":");
        if (sec < 10) {
            hms += "0";
        }
        hms += sec;
        return hms;
    }


    /**
     * 获取屏幕像素
     *
     * @return
     */
    public int getWidthPixels() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Configuration cf = context.getResources().getConfiguration();
        int ori = cf.orientation;
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            return displayMetrics.heightPixels;
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {// 竖屏
            return displayMetrics.widthPixels;
        }
        return 0;
    }

    public int dp2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}