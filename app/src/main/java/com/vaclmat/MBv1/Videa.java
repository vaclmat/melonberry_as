package com.vaclmat.MBv1;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class Videa extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {


    public static String substring = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videa);
    //    View sdCoordinationLayout = findViewById(R.id.sdCoordinatorLayout);
        Toolbar toolbar = findViewById(R.id.sd_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> finish());

        //Transfer parameter student_id from parent activity
        String linktv = getIntent().getStringExtra("linktv");
        assert linktv != null;
        substring = linktv.substring(Math.max(linktv.length() - 11, 0));

        String api_key = "Enter your API key here";

        YouTubePlayerFragment youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.youtube_fragment);

        youTubePlayerFragment.initialize(api_key, this);
        }

    /**
     *
     * @param provider The provider which was used to initialize the YouTubePlayer
     * @param youTubePlayer A YouTubePlayer which can be used to control video playback in the provider.
     * @param wasRestored Whether the player was restored from a previously saved state, as part of the YouTubePlayerView
     *                    or YouTubePlayerFragment restoring its state. true usually means playback is resuming from where
     *                    the user expects it would, and that a new video should not be loaded
     */
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION |
                YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);


        if(!wasRestored) {
            youTubePlayer.cueVideo(substring);
        }
    }

    /**
     *
     * @param provider The provider which failed to initialize a YouTubePlayer.
     * @param error The reason for this failure, along with potential resolutions to this failure.
     */
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {

        final int REQUEST_CODE = 1;

        if(error.isUserRecoverableError()) {
            error.getErrorDialog(this,REQUEST_CODE).show();
        } else {
            String errorMessage = String.format("There was an error initializing the YoutubePlayer (%1$s)", error);
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}


