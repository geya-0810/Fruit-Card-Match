package com.example.fruitcardmatch;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

public class Music extends Application {
    private static MediaPlayer mediaPlayer;
    private static boolean isMuted = false;

    public static MediaPlayer getMediaPlayer(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.music);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(1.0f, 1.0f);
            if (isMuted) {
                mediaPlayer.setVolume(0.0f, 0.0f); // if isMuted is true, set volume to 0
            }
        }
        return mediaPlayer;
    }

    public static void mute() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(0.0f, 0.0f);
        }
        isMuted = true; //update the muted status to muted
    }

    public static void unmute() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(1.0f, 1.0f);
        }
        isMuted = false; // update the muted status to not muted
    }

    public static boolean isMuted() {   //check the muted status is muted or not
        return isMuted;
    }
}
