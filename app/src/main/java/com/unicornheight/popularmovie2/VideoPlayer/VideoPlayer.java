package com.unicornheight.popularmovie2.VideoPlayer;

import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.unicornheight.popularmovie2.R;

/**
 * Created by deboajagbe on 5/13/17.
 */

public class VideoPlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private static String VIDEO_KEY = "";

    public VideoPlayer(YouTubePlayerView youTubeView, String key){
        youTubeView.initialize(VideoConfig.YOUTUBE_API_KEY, this);
        this.VIDEO_KEY = key;
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            youTubePlayer.cueVideo(VIDEO_KEY);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
                  Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
