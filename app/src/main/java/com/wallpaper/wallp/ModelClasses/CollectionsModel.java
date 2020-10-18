package com.wallpaper.wallp.ModelClasses;

public class CollectionsModel {
    String category;
    String wallpaperlink;


    public CollectionsModel(){

    }
    public CollectionsModel(String category, String wallpaperlink) {
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
