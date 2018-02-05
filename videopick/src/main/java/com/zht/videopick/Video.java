package com.zht.videopick;

/**
 * 作者：zhanghaitao on 2018/1/12 09:15
 * 邮箱：820159571@qq.com
 *
 * @describe:Video信息bean类
 */

public class Video {

    private static final long serialVersionUID = -7920222595800367956L;
    private int id;
    private String title;
    private String album;
    private String artist;
    private String displayName;
    private String mimeType;
    private String path;
    private long size;
    private long duration;
    private LoadedImage image;

    public Video() {
        super();
    }

    public Video(int id, String title, String album, String artist,
                 String displayName, String mimeType, String path, long size,
                 long duration) {
        super();
        this.id = id;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.displayName = displayName;
        this.mimeType = mimeType;
        this.path = path;
        this.size = size;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public LoadedImage getImage() {
        return image;
    }

    public void setImage(LoadedImage image) {
        this.image = image;
    }

    /////////////////////////////////////////////////////////////////////
    //////START
    /////////////////////////////////////////////////////////////////////

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public void Video() {
//
//    }
//
//    //将对象属性反序列化然后读取出来，注意属性的顺序必须按照序列化属性的顺序
//    protected Video(Parcel in) {
//        id = in.readInt();
//        title = in.readString();
//        album = in.readString();
//        artist = in.readString();
//        displayName = in.readString();
//        mimeType = in.readString();
//        path = in.readString();
//        size = in.readLong();
//        duration = in.readLong();
//    }
//
//    public static final Creator<Video> CREATOR = new Creator<Video>() {
//        @Override
//        public Video createFromParcel(Parcel in) {
//            return new Video(in);
//        }
//
//        @Override
//        public Video[] newArray(int size) {
//            return new Video[size];
//        }
//    };
//
//
//    //将对象属性进行序列化
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(title);
//        dest.writeString(album);
//        dest.writeString(artist);
//        dest.writeString(displayName);
//        dest.writeString(mimeType);
//        dest.writeString(path);
//        dest.writeLong(size);
//        dest.writeLong(duration);
//    }


}