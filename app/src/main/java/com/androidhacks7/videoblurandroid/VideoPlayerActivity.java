package com.androidhacks7.videoblurandroid;

import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.androidhacks7.videoblurandroid.extraeffects.VideoBlurEffect;
import com.sherazkhilji.videffects.NoEffect;
import com.sherazkhilji.videffects.view.VideoSurfaceView;

import java.io.IOException;

/**
 * @androidhacks7
 */
public class VideoPlayerActivity extends AppCompatActivity {

    public static final String TAG = VideoPlayerActivity.class.getSimpleName();

    private MediaPlayer mMediaPlayer;
    private VideoSurfaceView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMediaPlayer = new MediaPlayer();

        try {
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd("sample.mp4");
            final MediaMetadataRetriever mRetriever = new MediaMetadataRetriever();
            mRetriever.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            int width = Integer.parseInt(mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            int height = Integer.parseInt(mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));

            //divide the width and height by 5
            width /= 3;
            height /= 3;

            setContentView(R.layout.activity_video_player);
            //set the size of the surface to play on to 1/5 the width and height
            mVideoView = findViewById(R.id.video_surface_view);
            mVideoView.getHolder().setFixedSize(width, height);
            mMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
//            mVideoView.init(mMediaPlayer, new NoEffect());
            mVideoView.init(mMediaPlayer,
                    new VideoBlurEffect(17, width, height));

        } catch (IOException iOE) {
            Log.e(TAG, iOE.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.onResume();
    }
}
