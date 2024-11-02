package com.example.dalcet;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button playButton, stopButton, nextButton, playlistButton, settingsButton;
    private MediaPlayer mediaPlayer;
    private final int PLAYLIST_REQUEST_CODE = 1;
    private int currentSongIndex = -1; // Track the current song index

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);
        nextButton = findViewById(R.id.nextButton);
        playlistButton = findViewById(R.id.playlistButton);
        settingsButton = findViewById(R.id.settingsButton);

        // Initially load a default song
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.night_changes);

        // Play button logic
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this, "Playing Music", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Stop button logic
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release(); // Release the media player
                    mediaPlayer = null;  // Reset the media player
                    Toast.makeText(MainActivity.this, "Music Stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Next button logic
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextTrack();
            }
        });

        // Open PlaylistActivity to choose a song
        playlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlaylistActivity.class);
                startActivityForResult(intent, PLAYLIST_REQUEST_CODE);
            }
        });

        // Open SettingsActivity
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    // Handle the result from PlaylistActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLAYLIST_REQUEST_CODE && resultCode == RESULT_OK) {
            int selectedSongResId = data.getIntExtra("selectedSongResId", 0);
            if (selectedSongResId != 0) {
                // Stop any currently playing music and release the media player
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

                // Initialize the media player with the selected song and start playing
                mediaPlayer = MediaPlayer.create(MainActivity.this, selectedSongResId);
                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Playing Selected Song", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void playNextTrack() {
        // Stop current song if it's playing
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Increment the current song index
        currentSongIndex++;
        if (currentSongIndex > 3) { // Assuming you have 4 songs
            currentSongIndex = 0; // Loop back to the first song
        }

        // Play the next song
        int nextSongResId = 0;
        switch (currentSongIndex) {
            case 0: nextSongResId = R.raw.friends_theme; break;
            case 1: nextSongResId = R.raw.hips_dont_lie; break;
            case 2: nextSongResId = R.raw.i_like_me_better; break;
            case 3: nextSongResId = R.raw.night_changes; break;
        }

        // Initialize MediaPlayer and start playing the next song
        mediaPlayer = MediaPlayer.create(MainActivity.this, nextSongResId);
        mediaPlayer.start();
        Toast.makeText(MainActivity.this, "Playing Next Track", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
