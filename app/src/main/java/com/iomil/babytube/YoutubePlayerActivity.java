package com.iomil.babytube;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class YoutubePlayerActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener, PlayListAdapter.VideoClickListener
{

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    String mYoutubeAPIKey = "AIzaSyCPr3jFeOIvjURMtVdclayyx2COd_pzlBg";

    private YouTubePlayerView mYouTubeView;
    YouTubePlayer mPlayer;
    CustomYoutubeFragment mYoutubeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mYouTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        // Initializing video player with developer key
        mYouTubeView.initialize(mYoutubeAPIKey, this);

        /*mYoutubeFragment = (CustomYoutubeFragment)getFragmentManager().findFragmentById(R.id.youtubeplayer_fragment);
        CustomYoutubeFragment f = CustomYoutubeFragment.newInstance("your-video-url");
        getFragmentManager().beginTransaction().replace(R.id.youtubeplayer_fragment, f).commit();*/

        initPlayList();
    }

    private RecyclerView mPlayListView;
    private PlayListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    void initPlayList()
    {
        mPlayListView = (RecyclerView) findViewById(R.id.play_list_view);
        mLayoutManager = new LinearLayoutManager(this);
        mPlayListView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new PlayListAdapter(this, this, mPlayListView);

        mPlayListView.setAdapter(mAdapter);

        mPlayListView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager)
        {
            @Override
            public void onLoadMore(int current_page)
            {

            }


            public void onHide()
            {

            }
            public void onShow()
            {

            }
        });

        VideoManager videoManager = VideoManager.getInstance();
        videoManager.init(this);
        List<VideoItem> videos = new ArrayList<>();
        for(int i = 0; i < 12; i++)
            videos.addAll(videoManager.get());
        long seed = System.nanoTime();
        Collections.shuffle(videos, new Random(seed));
        mAdapter.appendQueueList(videos);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason)
    {
        if (errorReason.isUserRecoverableError())
        {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        }
        else
        {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,

                                        YouTubePlayer player, boolean wasRestored)
    {
        mPlayer = player;
        if (!wasRestored)
        {

            mPlayer = player;
            mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            //mPlayer.loadVideo("lc03JqnPbIk");
        }
    }

    @Override
    public void onClick(String videoId)
    {
        mPlayer.loadVideo(videoId);
    }
}
