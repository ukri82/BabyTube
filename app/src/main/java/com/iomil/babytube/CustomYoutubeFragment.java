package com.iomil.babytube;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomYoutubeFragment extends YouTubePlayerFragment
{

    String mYoutubeAPIKey = "AIzaSyCPr3jFeOIvjURMtVdclayyx2COd_pzlBg";
    YouTubePlayer mPlayer;

    public CustomYoutubeFragment()
    {
        // Required empty public constructor
    }

    public static CustomYoutubeFragment newInstance(String url) {

        CustomYoutubeFragment f = new CustomYoutubeFragment();

        Bundle b = new Bundle();
        b.putString("url", url);

        f.setArguments(b);
        f.init();

        return f;
    }


    private void init() {

        initialize(mYoutubeAPIKey, new YouTubePlayer.OnInitializedListener() {


            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    mPlayer = player;
                    //player.cueVideo(getArguments().getString("url"));
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
            {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_youtube, container, false);
    }

}
