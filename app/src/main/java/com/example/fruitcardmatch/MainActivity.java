package com.example.fruitcardmatch;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView button, guide;
    ImageView btnVolume, book;
    MediaPlayer mP;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mP = Music.getMediaPlayer(this);

        button = findViewById(R.id.btnStart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Game.class));
            }
        });

        mP.start();
        btnVolume = findViewById(R.id.btnMute);
        btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Music.isMuted()) {   //if music is muted then unmute
                    Music.unmute();
                    btnVolume.setImageResource(R.drawable.volume);
                    btnVolume.setContentDescription("Volume On");
                } else {   //if music is not muted then mute
                    Music.mute();
                    btnVolume.setImageResource(R.drawable.mute);
                    btnVolume.setContentDescription("Volume Off");
                }
            }
        });

        guide = findViewById(R.id.description);
        book = findViewById(R.id.btnBook);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen) {
                    guide.setVisibility(View.INVISIBLE);
                    book.setImageResource(R.drawable.book_close);
                    isOpen = false;
                } else {
                    guide.setVisibility(View.VISIBLE);
                    book.setImageResource(R.drawable.book_open);
                    isOpen = true;
                }
            }
        });

    }

}
