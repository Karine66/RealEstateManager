package com.openclassrooms.realestatemanager.createEstate;

import androidx.appcompat.app.AppCompatActivity;


import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;

import com.openclassrooms.realestatemanager.databinding.ActivityVideoBinding;

public class VideoActivity extends AppCompatActivity {

    private ActivityVideoBinding activityVideoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for View binding
        activityVideoBinding = ActivityVideoBinding.inflate(getLayoutInflater());
        View view = activityVideoBinding.getRoot();
        setContentView(view);

        displayVideo();
    }

       public void displayVideo() {
        String videoPath = "android.resource://"+ getPackageName() + "/";
        Uri uri = Uri.parse(videoPath);
       activityVideoBinding.videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        activityVideoBinding.videoView.setMediaController(mediaController);
        mediaController.setAnchorView(activityVideoBinding.videoView);

    }
}