package com.example.fitbit;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Friday_Advanced extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friday_advanced);

// Define an array of video URIs
        String path = "android.resource://" + getPackageName() + "/" + R.raw.chest_dumbellpress;
        String path1 = "android.resource://" + getPackageName() + "/" + R.raw.chest_inclinebarbellpress;
        String path2 = "android.resource://" + getPackageName() + "/" + R.raw.chest_dumbellflys;
        String path3 = "android.resource://" + getPackageName() + "/" + R.raw.chest_inclinelandminepress;
        String path4 = "android.resource://" + getPackageName() + "/" + R.raw.shoulder_barbelloverheadpress;
        String path5 = "android.resource://" + getPackageName() + "/" + R.raw.shoulder_dumbellfrontrises;
        String path6 = "android.resource://" + getPackageName() + "/" + R.raw.shoulder_closegriphighpull;
        String path7 = "android.resource://" + getPackageName() + "/" + R.raw.tricep_skullcrusher;
        String path8 = "android.resource://" + getPackageName() + "/" + R.raw.tricep_pushdown;
        String path9 = "android.resource://" + getPackageName() + "/" + R.raw.tricep_closegripbenchpress;



        String[] videoUris = { path, path1, path2, path3, path4,path5,path6,path7,path8 };

// Initialize the current video index to 0 (for bench video)
        final int[] currentVideoIndex = {0};

// Set the video URI to the first video in the array (bench video)
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(videoUris[currentVideoIndex[0]]));

// Get references to the button and text view
        Button myButton = findViewById(R.id.Done);
        TextView myTextView = findViewById(R.id.Variation);

// Define an array of exercise sequence
        String[] sequence = {"Incline BarbellPress", "Dumbbell Fly", "Incline LandMine Press", "Barbell OverHead Press", "Dumbbell FrontRaises", "CloseGrip HighPull", "Skull Crusher", "Cabel PushDown",  "CloseGrip BenchPress"  };
        final int[] counter = {0};
        boolean isDoneClicked = false;

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter[0] < sequence.length) {
                    // Update the exercise variation text
                    myTextView.setText(sequence[counter[0]]);
                    // Increment the current video index and set the next video URI
                    currentVideoIndex[0]++;
                    if (currentVideoIndex[0] >= videoUris.length) {
                        // If the end of the array is reached, start over from the beginning
                        currentVideoIndex[0] = 0;
                    }
                    videoView.setVideoURI(Uri.parse(videoUris[currentVideoIndex[0]]));
                    videoView.start();
                    counter[0]++;
                } else {
                    myButton.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Congratulation !! You have Completed Push Workout", Toast.LENGTH_SHORT).show();
                    counter[0] = 0; // reset counter to start sequence over


                    // Launch the previous activity after a 2-second delay
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), Advanced_Level.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1500); // 2-second delay (in milliseconds)
                }
            }
        });

// Start playing the first video (bench)

        videoView.setVideoURI(Uri.parse(videoUris[currentVideoIndex[0]]));
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Restart the video from the beginning
                videoView.start();
                mp.setLooping(true);
            }
        });
        videoView.start();



    }
}
