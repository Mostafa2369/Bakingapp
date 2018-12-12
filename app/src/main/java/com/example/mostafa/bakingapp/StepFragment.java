package com.example.mostafa.bakingapp;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepFragment extends Fragment implements ExoPlayer.EventListener {
    private String mStep;
    private SimpleExoPlayer mExoPlayer;
    private String url;
    private String[] mStepList;
    boolean playWhenReady;
    private int num;
    private long playerStopPosition;
    private static final String EXO_CURRENT_POSITION = "current_position";
    private String[] part;
    private long exo_current_position = 0;
    private int index;
    private boolean playerStopped = false;
    View rootView;

    @BindView(R.id.stepDescription)
    TextView mStepDes;

    @BindView(R.id.playerView) SimpleExoPlayerView  mPlayerView ;
    public StepFragment() {

    }
    @Override
    public void onResume() {
        super.onResume();


            if (url != null)
                initializePlayer(Uri.parse(url));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("list", mStepList);
        outState.putInt("num", num);
        outState.putBoolean("play",mPlayerView.getPlayer().getPlayWhenReady());

        outState.putInt("index", index);
        outState.putLong(EXO_CURRENT_POSITION,mPlayerView.getPlayer().getCurrentPosition());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.step_details_list, container, false);

        ButterKnife.bind(this, rootView);


        if (savedInstanceState != null) {
            releasePlayer();
            mStepList = savedInstanceState.getStringArray("list");
            num = savedInstanceState.getInt("num");
            index = savedInstanceState.getInt("index");
            playWhenReady = savedInstanceState.getBoolean("play");
            exo_current_position = savedInstanceState.getLong(EXO_CURRENT_POSITION);

        }else
            playWhenReady=true;


        index = num - 1;
        if (mStepList != null)
            mStep = mStepList[index];

        part = mStep.split("@");
        mStepDes.setText(part[1]);
        url = "";
        if (part.length > 2) {
            if (part[2] != null)
                url = part[2];
            else if (part[3] != null)
                url = part[3];
        }

        if (url.length() > 10)
        {  Log.d("StepFragment","on save after 2 "+exo_current_position);
            initializePlayer(Uri.parse(url));
        }
        else
            mPlayerView.setVisibility(View.GONE);


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (url != null)
            initializePlayer(Uri.parse(url));

    }

    public void setStep(String[] data) {

        mStepList = data;

    }

    public void setNum(int no) {
        num = no;
    }


    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), "bakingapp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
           mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(exo_current_position);
            Log.d("StepFragment","on save after 3 "+playWhenReady);

        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            playerStopPosition = mExoPlayer.getCurrentPosition();

            mExoPlayer.release();
            mExoPlayer = null;

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        Log.d("StepFragment","on save dess"+ playWhenReady);
    }

    @Override
    public void onPause() {
        super.onPause();

            releasePlayer();


    }

    @Override
    public void onStop() {
        super.onStop();

            releasePlayer();

    }


    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    public void onLoadingChanged(boolean isLoading) {

    }

    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    public void onPlayerError(ExoPlaybackException error) {

    }

    public void onPositionDiscontinuity() {

    }
}
