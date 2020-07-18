package com.openclassrooms.realestatemanager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding;
import com.openclassrooms.realestatemanager.databinding.EstateFormBinding;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity {


    private static final int RC_CAMERA_AND_STORAGE = 100;
    private static final String[] CAM_AND_READ_EXTERNAL_STORAGE =
            {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    private MaterialAlertDialogBuilder builder;
    private MaterialAlertDialogBuilder builderVideo;
    private EstateFormBinding estateFormBinding;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        estateFormBinding = EstateFormBinding.inflate(getLayoutInflater());
//        View view = estateFormBinding.getRoot();
//        setContentView(view);
//    }
    protected void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void configureUpButton() {
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setDisplayHomeAsUpEnabled(true);
    }


    //For permissions camera en read external storage
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 2 - Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_CAMERA_AND_STORAGE)
    protected void methodRequiresTwoPermission() {

        if (EasyPermissions.hasPermissions(this, CAM_AND_READ_EXTERNAL_STORAGE)) {
//            selectImage();
//            selectVideo();

            Log.d("Permissions", "permissions granted");
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "This application need permissions to access",
                    RC_CAMERA_AND_STORAGE, CAM_AND_READ_EXTERNAL_STORAGE);
        }
    }

    //For alert dialog for choose take photo or choose in gallery
    protected void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Add pictures");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();

                }
            }
        });

        builder.show();
    }

    //For alert dialog for choose take video or choose video
    protected void selectVideo() {
        final CharSequence[] options = {"Take Video", "Choose Video", "Cancel"};

        builderVideo = new MaterialAlertDialogBuilder(this);
        builderVideo.setTitle("Add video");
        builderVideo.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Video")) {
                    Intent takeVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(takeVideo, 2);

                } else if (options[item].equals("Choose Video")) {
                    Intent pickVideo = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickVideo, 3);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();

                }
            }
        });
        builderVideo.show();
    }

//    public void displayVideo() {
//        String videoPath = "android.resource://"+ getPackageName() + "/";
//        Uri uri = Uri.parse(videoPath);
//        estateFormBinding.videoView.setVideoURI(uri);
//
//        MediaController mediaController = new MediaController(this);
//        estateFormBinding.videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(estateFormBinding.videoView);
//
//    }
}
