package com.openclassrooms.realestatemanager.createEstate;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddActivity extends BaseActivity implements View.OnClickListener {

    private static final int CAMERA_PERM_CODE = 100;
//    private static final int CAMERA_REQUEST_CODE = 200;
//    private static final int GALLERY_REQUEST_CODE = 300;

//    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    private ActivityAddBinding activityAddBinding;
    private DatePickerDialog mUpOfSaleDateDialog;
    private DatePickerDialog mSoldDate;
    private SimpleDateFormat mDateFormat;
    private View view;
//    private String currentPhotoPath;
//    private InputStream inputStreamImg;
//    private Bitmap bitmap;
//    private  File destination = null;
//    private String imgPath = null;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for view binding
        activityAddBinding = ActivityAddBinding.inflate(getLayoutInflater());
        View view = activityAddBinding.getRoot();
        setContentView(view);

        //For toolbar
        this.configureToolbar();
        this.configureUpButton();
        this.dropDownAdapters();
        this.setDateField();
        this.onClickValidateBtn();
        this.onClickPhotoBtn();

//        this.onClickGalleryBtn();
        //for title toolbar
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setTitle("Create Estate");
        //For date picker
        mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    }
    
    //for adapter generic
    private ArrayAdapter<String> factoryAdapter(int resId) {
        return new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, getResources().getStringArray(resId));
    }

    //For adapters dropdown
    public void dropDownAdapters() {

        activityAddBinding.etEstate.setAdapter(factoryAdapter(R.array.ESTATES));
        activityAddBinding.etRooms.setAdapter(factoryAdapter(R.array.ROOMS));
        activityAddBinding.etBedrooms.setAdapter(factoryAdapter(R.array.BEDROOMS));
        activityAddBinding.etBathrooms.setAdapter(factoryAdapter(R.array.BATHROOMS));
        activityAddBinding.etAgent.setAdapter(factoryAdapter(R.array.AGENT));
    }
    // for date picker
    private void setDateField() {
        activityAddBinding.upOfSaleDate.setOnClickListener(this);
        activityAddBinding.soldDate.setOnClickListener(this);
        //For up of sale date
        Calendar newCalendar = Calendar.getInstance();
        mUpOfSaleDateDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            activityAddBinding.upOfSaleDate.setText(mDateFormat.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        //For sold date
         mSoldDate = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            activityAddBinding.soldDate.setText(mDateFormat.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
// for click on date picker
    @Override
    public void onClick(View view) {
        if (view == activityAddBinding.upOfSaleDate) {
            mUpOfSaleDateDialog.show();
            mUpOfSaleDateDialog.getDatePicker().setMaxDate((Calendar.getInstance().getTimeInMillis()));

        } else if (view == activityAddBinding.soldDate) {
            mSoldDate.show();
            mSoldDate.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        }
    }
// for click on fab validate btn

    public void onClickValidateBtn() {
        activityAddBinding.validateFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"You're new Estate is created", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    //For manage photos
    //For click on photo btn
    public void onClickPhotoBtn() {
      activityAddBinding.photoBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             askCameraPermissions();
             selectImage();
          }
     });
    }

//    // Select image from camera and gallery
//    private void selectImage() {
//
//        final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Select Option");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                        if (options[item].equals("Take Photo")) {
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            dispatchTakePictureIntent();
//                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
//
//                        } else if (options[item].equals("Choose From Gallery")) {
//                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
//
//                        } else if (options[item].equals("Cancel")) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//                builder.show();
//        }


    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               selectImage();
            }else {
                Snackbar.make(view, "Camera Permission is required to use camera", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_CAMERA && data != null && data.getData() != null) {
//            if (resultCode == Activity.RESULT_OK) {
//                File file = new File(currentPhotoPath);
//                Objects.requireNonNull(activityAddBinding.photoImage1).setImageURI(Uri.fromFile(file));
//                Log.d("TestUri", "Uri image is" + Uri.fromFile(file));
//
//                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                Uri contentUri = Uri.fromFile(file);
//                mediaScanIntent.setData(contentUri);
//                this.sendBroadcast(mediaScanIntent);
//            }
//        }
//        if (requestCode == PICK_IMAGE_GALLERY && data != null && data.getData() != null) {
//            if (resultCode == Activity.RESULT_OK) {
//                Uri contentUri = Objects.requireNonNull(data).getData();
//                String timeStamp = new SimpleDateFormat("ddMMyyyy", Locale.FRANCE).format(new Date());
//                String imageFileName = "JPEG" + timeStamp + "." + getFileExt(contentUri);
//                Log.d("Test uri gallery", "onActivityResult : Gallery Image Uri:" + imageFileName);
//                Objects.requireNonNull(activityAddBinding.photoImage1).setImageURI(contentUri);
//            }
//        }
//    }
//
//    private String getFileExt(Uri contentUri) {
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(contentResolver.getType(contentUri));
//    }
//
//    private File createImageFile() throws IOException {
//        //Create an image file name
//        String timeStamp = new SimpleDateFormat("ddMMyyyy", Locale.FRANCE).format(new Date());
//        String imageFileName = "JPEG" + timeStamp + "_";
//        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName, /*prefix*/
//                ".jpg", /*suffix*/
//                storageDir /*directory*/
//        );
//        //Save file : path for use with ACTION_VIEW intent
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    private void dispatchTakePictureIntent() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////      startActivityForResult(intent, PICK_IMAGE_CAMERA);
//        //Ensure that there's camera activity to handle the intent
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            //Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                ex.getMessage();
//            }
//            //Continue only if the file was successfully created
//            if (photoFile != null) {
//                Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), "com.openclassrooms.realestatemanager.fileprovider", photoFile);
//
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                Log.d("PhotoUri","photoUri =" +photoUri );
////                startActivityForResult(intent, PICK_IMAGE_CAMERA);
//
//            }
//        }
//    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                        activityAddBinding.photoImage1.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            Log.d("filePathColumn", "file path column" + Arrays.toString(filePathColumn));
                            Log.d("selectedImage", "selectedImage" +selectedImage);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                activityAddBinding.photoImage1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                                Log.d("picturePath", "picture path is :" +picturePath);
                            }
                        }

                    }
                    break;
            }
        }
    }
}




