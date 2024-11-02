package com.example.dalcet;

import com.example.dalcet.adapters.SongAdapter; // Adjust according to your package name
import com.example.dalcet.R; // Adjust according to your package name
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private Button backButton;
    private RecyclerView songListView;
    private SongAdapter songAdapter;
    private List<String> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        backButton = findViewById(R.id.backButton);
        songListView = findViewById(R.id.songListView);

        // Initialize the song list with song names
        songList = new ArrayList<>();
        songList.add("friends_theme.mp3"); // Make sure this corresponds to actual resource names
        songList.add("hips_dont_lie.mp3");
        songList.add("i_like_me_better.mp3");
        songList.add("night_changes.mp3");

        // Set up RecyclerView
        songAdapter = new SongAdapter(songList, songName -> {
            // Handle song click
            // Use the resource ID to play the corresponding song
            int songResId = getSongResourceId(songName);
            if (songResId != -1) {
                MediaPlayer mediaPlayer = MediaPlayer.create(PlaylistActivity.this, songResId);
                mediaPlayer.start();
            }
        });
        songListView.setLayoutManager(new LinearLayoutManager(this));
        songListView.setAdapter(songAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close current activity and return to previous
            }
        });
    }

    // Method to get the resource ID for a song based on its name
    private int getSongResourceId(String songName) {
        switch (songName) {
            case "Song 1":
                return R.raw.friends_theme; // Replace with actual resource name
            case "Song 2":
                return R.raw.hips_dont_lie;
            case "Song 3":
                return R.raw.i_like_me_better;
            case "Song 4":
                return R.raw.night_changes;
            default:
                return -1; // Return -1 if song not found
        }
    }
}
