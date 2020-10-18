package com.wallpaper.wallp.ModelClasses;

public class TrendModel {
    String category;
    String wallpaperlink;


    public TrendModel(){

    }
    public TrendModel(String category, String wallpaperlink) {
        this.category = category;
        this.wallpaperlink = wallpaperlink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWallpaperlink() {
        return wallpaperlink;
    }

    public void setWallpaperlink(String wallpaperlink) {
        this.wallpaperlink = wallpaperlink;
    }
}
