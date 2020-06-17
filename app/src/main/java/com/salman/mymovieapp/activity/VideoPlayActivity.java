package com.salman.mymovieapp.activity;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerListener;
import com.salman.mymovieapp.R;
import com.salman.mymovieapp.adapter.ExtraVideosRecyclerAdapter;
import com.salman.mymovieapp.model.MovieVideosResults;
import com.salman.mymovieapp.utils.FullScreenHelper;

import java.util.ArrayList;

public class VideoPlayActivity extends AppCompatActivity {
    private static final String API_KEY = "AIzaSyBkTuTV_NaS0gx2ob7eKRdFDh599oEdhMo";
    ThumbnailView thumbnailView;
    AppCompatTextView videoTitle, noResult;
    YouTubePlayerView playerView;
    ProgressBar progressBar;
    RecyclerView otherVideosReycyclerView;
    FullScreenHelper fullScreenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        Intent intent = getIntent();

        ThumbnailLoader.initialize(API_KEY);
        fullScreenHelper = new FullScreenHelper(this);
        thumbnailView = findViewById(R.id.video_thumbnailview);
        videoTitle = findViewById(R.id.video_title);
        noResult = findViewById(R.id.no_video);
        playerView = findViewById(R.id.video_playerview);
        otherVideosReycyclerView = findViewById(R.id.other_videos_RV);
        otherVideosReycyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        progressBar = findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(0XFFFFFFFF, PorterDuff.Mode.MULTIPLY);

        // get the arraylist and position
        if (intent != null && intent.getExtras() != null){
            ArrayList<MovieVideosResults> movieVideosResultsArrayList = intent.getExtras().getParcelableArrayList("video");
            int position = Integer.parseInt(intent.getExtras().getString("position"));
            if (movieVideosResultsArrayList != null && movieVideosResultsArrayList.size() > 0){
                final String videoId = movieVideosResultsArrayList.get(position).getKey();
                String title = movieVideosResultsArrayList.get(position).getName();
                if (title != null){
                    videoTitle.setText(title);
                }
                if (videoId != null){
                    String baseUrl = "https://www.youtube.com/watch?v=";
                    thumbnailView.loadThumbnail(baseUrl + videoId);
                    playerView.initialize(new YouTubePlayerInitListener() {
                        @Override
                        public void onInitSuccess(@NonNull final YouTubePlayer youTubePlayer) {
                            youTubePlayer.addListener(new YouTubePlayerListener() {
                                @Override
                                public void onReady() {
                                    // when video is ready to play hide the thumbnail and progress ba
                                    thumbnailView.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);

                                    // show the youtube player
                                    playerView.setVisibility(View.VISIBLE);
                                    if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED){
                                        youTubePlayer.loadVideo(videoId, 0);
                                    } else {
                                        youTubePlayer.cueVideo(videoId, 0);
                                    }
                                }

                                @Override
                                public void onStateChange(@NonNull PlayerConstants.PlayerState state) {

                                }

                                @Override
                                public void onPlaybackQualityChange(@NonNull PlayerConstants.PlaybackQuality playbackQuality) {

                                }

                                @Override
                                public void onPlaybackRateChange(@NonNull PlayerConstants.PlaybackRate playbackRate) {

                                }

                                @Override
                                public void onError(@NonNull PlayerConstants.PlayerError error) {

                                }

                                @Override
                                public void onApiChange() {

                                }

                                @Override
                                public void onCurrentSecond(float second) {

                                }

                                @Override
                                public void onVideoDuration(float duration) {

                                }

                                @Override
                                public void onVideoLoadedFraction(float loadedFraction) {

                                }

                                @Override
                                public void onVideoId(@NonNull String videoId) {

                                }
                            });

                            playerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
                                @Override
                                public void onYouTubePlayerEnterFullScreen() {
                                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                    fullScreenHelper.enterFullScreen();
                                }

                                @Override
                                public void onYouTubePlayerExitFullScreen() {
                                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                    fullScreenHelper.exitFullScreen();
                                }
                            });
                        }
                    }, true);

                    // load other videos in recycler view
                    ArrayList<MovieVideosResults> movieVideosResultsList = new ArrayList<>(movieVideosResultsArrayList);
                    // remove current video from the list
                    movieVideosResultsList.remove(position);
                    if (movieVideosResultsList.size() > 0){
                        noResult.setVisibility(View.GONE);
                        ExtraVideosRecyclerAdapter adapter = new ExtraVideosRecyclerAdapter(VideoPlayActivity.this, movieVideosResultsList);
                        otherVideosReycyclerView.setAdapter(adapter);
                        otherVideosReycyclerView.setVisibility(View.VISIBLE);
                    } else {
                        otherVideosReycyclerView.setVisibility(View.GONE);
                        noResult.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

    }

    // exit the fullscreen on back presed

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (playerView.isFullScreen()){
            playerView.exitFullScreen();
        } else {
            otherVideosReycyclerView.setVisibility(View.GONE);
            playerView.setVisibility(View.GONE);
            thumbnailView.setVisibility(View.VISIBLE);
        }
    }
}
