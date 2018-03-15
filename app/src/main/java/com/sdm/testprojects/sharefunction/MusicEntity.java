package com.sdm.testprojects.sharefunction;

/**
 * Created by shidongming on 18-2-28.
 */

public class MusicEntity {

    private String id;
    private String name;
    private String title;
    private String duration;
    private String singer;//歌手名
    private String album;//专辑名
    private String year;//年代

    private String type;//格式
    private String size;//大小
    private String dataUrl;//路径

    public MusicEntity() {
    }

    public MusicEntity(String name, String title, String duration, String singer, String album, String year, String type, String size, String dataUrl) {
        this.name = name;
        this.title = title;
        this.duration = duration;
        this.singer = singer;
        this.album = album;
        this.year = year;
        this.type = type;
        this.size = size;
        this.dataUrl = dataUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    @Override
    public String toString() {
        return "MusicEntity{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                ", singer='" + singer + '\'' +
                ", album='" + album + '\'' +
                ", year='" + year + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", dataUrl='" + dataUrl + '\'' +
                '}';
    }
}
