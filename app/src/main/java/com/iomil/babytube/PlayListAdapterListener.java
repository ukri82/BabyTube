package com.iomil.babytube;

import android.view.View;

/**
 * Created by u on 27.09.2015.
 */
public interface PlayListAdapterListener
{
    public void onItemSelected(String aYoutubeId_in);
    public void onItemDeleted(String aYoutubeId_in);

}
