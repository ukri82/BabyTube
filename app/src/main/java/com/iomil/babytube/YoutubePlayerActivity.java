package com.iomil.babytube;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

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
        List<VideoItem> videos = new ArrayList<>();
        for(int i = 0; i < 8; i++)
        {
            videos.add(new VideoItem("lc03JqnPbIk", "lc03JqnPbIk"));
            videos.add(new VideoItem("Sietpr9Wol8", "Sietpr9Wol8"));
            videos.add(new VideoItem("A3u2y9XJ0Fw", "A3u2y9XJ0Fw"));
            videos.add(new VideoItem("6gRMAa70Yfo", "6gRMAa70Yfo"));
            videos.add(new VideoItem("AfCAVqnV5Jw", "AfCAVqnV5Jw"));
            videos.add(new VideoItem("Q2FwhvKXeq4", "Q2FwhvKXeq4"));
            videos.add(new VideoItem("Pof3oYF4ahg", "Pof3oYF4ahg"));
            videos.add(new VideoItem("E3djTVRfIF0", "E3djTVRfIF0"));

        }
        mAdapter.appendQueueList(videos);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
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

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            mPlayer = player;
            mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            mPlayer.loadVideo("lc03JqnPbIk");

            // Hiding player controls
            //player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    @Override
    public void onClick(String videoId)
    {
        mPlayer.loadVideo(videoId);
    }
}
