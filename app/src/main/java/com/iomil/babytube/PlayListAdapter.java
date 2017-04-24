package com.iomil.babytube;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by u on 05.12.2016.
 */

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder>
{
    public interface VideoClickListener
    {
        public void onClick(String videoId);
    }

    VideoClickListener mListener;

    private ArrayList<VideoItem> mDataset = new ArrayList<>();


    private int myPreviousPosition = 0;
    Activity mActivity;
    RecyclerView mParentView;

    public PlayListAdapter(Activity activity, VideoClickListener listener, RecyclerView parentView)
    {
        mActivity = activity;
        mParentView = parentView;
        mListener = listener;
    }


    public void appendQueueList(List<VideoItem> data)
    {
        int aNumItems = mDataset.size();
        mDataset.addAll(data);

        notifyItemRangeInserted(aNumItems, data.size());
    }


    public String getFirst()
    {
        return mDataset.get(0).mId;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public View mParentView;
        public ImageView mPictureView;
        public TextView mDescrView;
        public ViewHolder(View v)
        {
            super(v);
            mParentView = v;
            mPictureView = (ImageView)v.findViewById(R.id.image_view);
            mDescrView = (TextView)v.findViewById(R.id.video_descr);
        }

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_card, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    private void setImage(ImageView imView, String videoId)
    {
        try
        {
            // get input stream
            String imageName = "images/" + videoId + ".jpg";
            InputStream ims = mActivity.getAssets().open(imageName);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imView.setImageDrawable(d);
            ims .close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.mParentView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onClick(mDataset.get(position).mId);
            }
        });

        holder.mDescrView.setText(mDataset.get(position).mId);

        //setImage(holder.mPictureView, mDataset.get(position).mId);
        new DownloadImageTask(holder.mPictureView)
                .execute("http://img.youtube.com/vi/" + mDataset.get(position).mId + "/default.jpg");


        holder.mPictureView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onClick(mDataset.get(position).mId);
            }
        });

    }


    @Override
    public int getItemCount()
    {
        return mDataset.size();
    }


}
