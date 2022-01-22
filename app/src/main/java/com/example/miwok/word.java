package com.example.miwok;

public class word {
    private String mDefaultTranslation;


    private String mMiwkoTranslation;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private int mAudioResourceId;

    private static final int NO_IMAGE_PROVIDED= -1;


    public word(String defaultTranslation, String miwkoTranslation,int audioResourceId)
    {
        mDefaultTranslation = defaultTranslation;
        mMiwkoTranslation = miwkoTranslation;
        mAudioResourceId = audioResourceId;
    }

    public word(String defaultTranslation, String miwkoTranslation, int imageResourceId, int audioResourceId)
    {
        mDefaultTranslation = defaultTranslation;
        mMiwkoTranslation = miwkoTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getmMiwkoTranslation() {
        return mMiwkoTranslation;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getmAudioResourceId(){
        return mAudioResourceId;
    }
}

