package com.iomil.babytube;

import android.content.Context;
import android.database.Cursor;

import com.iomil.babytube.db.CBDbInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by u on 30.10.2016.
 */

public class VideoManager
{
    private static final VideoManager INSTANCE = new VideoManager();

    public static VideoManager getInstance()
    {
        return INSTANCE;
    }


    Context mContext;
    CBDbInterface mDbHelper;

    List<VideoItem> mVideos = new ArrayList<>();

    public void init(Context c)
    {
        mContext = c;

        mDbHelper = new CBDbInterface(mContext);
        mDbHelper.createDatabase();
        mDbHelper.open();

        readVideos();
    }

    public void close()
    {
        mDbHelper.close();
    }

    public List<VideoItem> get()
    {
        return mVideos;
    }

    public void add(String videoId)
    {
        mVideos.add(new VideoItem(videoId, videoId));

        mDbHelper.addVideo(videoId);
    }


    public void readVideos()
    {
        Cursor player = mDbHelper.getVideos();

        if (player != null )
        {
            if  (player.moveToFirst())
            {
                do
                {
                    createVideoEntry(player);
                }
                while (player.moveToNext());
            }
        }
        player.close();
    }

    private void createVideoEntry(Cursor videoCursor)
    {
        int id = videoCursor.getInt(videoCursor.getColumnIndex("id"));
        String videoId = videoCursor.getString(videoCursor.getColumnIndex("video_id"));

        mVideos.add(new VideoItem(videoId, videoId));
    }
}
