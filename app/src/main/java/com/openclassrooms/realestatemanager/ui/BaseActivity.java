package com.openclassrooms.realestatemanager.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.PhotoList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity {


    private static final int RC_CAMERA_AND_STORAGE = 100;
    private static final String[] CAM_AND_READ_EXTERNAL_STORAGE =
            {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    private MaterialAlertDialogBuilder builder;
    private MaterialAlertDialogBuilder builderVideo;
    private String currentPhotoPath;
    private Context context;
    protected ActionBar ab;


    protected void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void configureUpButton() {
        ab = getSupportActionBar();
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
            Log.d("Permissions", "Permissions granted");
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "This application need permissions to access",
                    RC_CAMERA_AND_STORAGE, CAM_AND_READ_EXTERNAL_STORAGE);
        }
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
                    startActivityForResult(takeVideo, 3);

                } else if (options[item].equals("Choose Video")) {
                    Intent pickVideo = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickVideo, 4);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();

                }
            }
        });
        builderVideo.show();
    }


}
