package com.iomil.babytube.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Dell on 4/23/2016.
 */


public class CBDbInterface
{
    protected static final String TAG = "CBDbInterface";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public CBDbInterface(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public CBDbInterface createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public CBDbInterface open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getWritableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public Cursor getVideos()
    {
        String[] params = new String[]{};

        String sql = "SELECT *  FROM video";
        return rawExecute(sql, params);
    }

    public long addVideo(String video)
    {
        ContentValues values = new ContentValues();
        values.put("video_id", video);

        return mDb.insert("video", null, values);
    }

    public Cursor rawExecute(String sql, String[] params)
    {
        try
        {
            Cursor mCur = mDb.rawQuery(sql, params);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "rawExecute >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}
