package com.coders.initiative.umoney.model;

/**
 * Created by Kira on 8/27/2016.
 */
public class NavModel {

    private long mId;
    private String mImageURL;
    private String mText;
    private int mIconRes;
    private int iconDrawable;

    public NavModel() {
    }

    public NavModel(long id, String imageURL, String text, int iconRes) {
        mId = id;
        mImageURL = imageURL;
        mText = text;
        mIconRes = iconRes;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getIconRes() {
        return iconDrawable;
    }

    public void setIconRes(int iconRes) {
        iconDrawable = iconRes;
    }

    @Override
    public String toString() {
        return mText;
    }
}
