package com.openclassrooms.realestatemanager.ui.createEstate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;
import com.openclassrooms.realestatemanager.databinding.EstateFormBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.PhotoDescription;
import com.openclassrooms.realestatemanager.models.PhotoList;
import com.openclassrooms.realestatemanager.ui.BaseActivity;
import com.openclassrooms.realestatemanager.ui.detailDescription.DetailFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class AddActivity extends BaseActivity implements View.OnClickListener {

    private static final int CAMERA_PERM_CODE = 100;


    private static final int RC_CAMERA_AND_STORAGE = 100;
    private static final String[] CAM_AND_READ_EXTERNAL_STORAGE =
            {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_TAKE_PHOTO = 200;
    protected final int PICK_IMAGE_CAMERA = 1;
    protected final int PICK_IMAGE_GALLERY = 2;
    protected final int PICK_VIDEO_CAMERA = 3;
    protected final int PICK_VIDEO_GALLERY = 4;

    private ActivityAddPhotoItemBinding activityAddPhotoItemBinding;
    private ActivityAddBinding activityAddBinding;
    private EstateFormBinding estateFormBinding;
    private DatePickerDialog mUpOfSaleDateDialog;
    private DatePickerDialog mSoldDate;
    private SimpleDateFormat mDateFormat;
    private View view;
    private Context context;

    private EstateViewModel estateViewModel;
    private VideoView videoView;
    private static final String VIDEO_DIRECTORY = "/realEstateManager";
    private int GALLERY = 1, CAMERA = 2;

    private PhotoAdapter adapter;
    private long mandateNumberID;
    private List<Uri> listPhoto;
    private RequestManager glide;
    private Bitmap selectedImage;
    private String currentPhotoPath;
    private Uri photoUri;
    private Uri uriCamera;
    private String path;
    private Estate estate;
    private MaterialAlertDialogBuilder builder;
    private MaterialAlertDialogBuilder builderVideo;
    private PhotoList photoList;
    private Uri contentUri;
    private PhotoList photo = new PhotoList();
    private DetailFragment detailFragment;

    private PhotoDescription photoText = new PhotoDescription();
    private PhotoList video = new PhotoList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for view binding
        activityAddBinding = ActivityAddBinding.inflate(getLayoutInflater());
        estateFormBinding = activityAddBinding.includeForm;

        View view = activityAddBinding.getRoot();
        setContentView(view);

        this.methodRequiresTwoPermission();
        this.configureToolbar();
        this.configureUpButton();
        this.dropDownAdapters();
        this.setDateField();
        this.configureViewModel();
        this.configureRecyclerView();
        this.onClickPhotoBtn();
        this.onClickVideoBtn();
        this.onClickValidateBtn();


        //for title toolbar
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setTitle("Create Estate");
        //For date picker
        mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        //For hide mandate number
        estateFormBinding.etMandate.setVisibility(View.INVISIBLE);
        estateFormBinding.inputMandate.setVisibility(View.INVISIBLE);


    }


    public void configureRecyclerView() {

        listPhoto = new ArrayList<>();

        adapter = new PhotoAdapter(listPhoto, Glide.with(this), photoText.getPhotoDescription());
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        Objects.requireNonNull(estateFormBinding.rvPhoto).setLayoutManager(horizontalLayoutManager);
        estateFormBinding.rvPhoto.setAdapter(adapter);
    }

    //for adapter generic
    private ArrayAdapter<String> factoryAdapter(int resId) {
        return new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, getResources().getStringArray(resId));
    }

    //For adapters dropdown
    public void dropDownAdapters() {

        estateFormBinding.etEstate.setAdapter(factoryAdapter(R.array.ESTATES));
        estateFormBinding.etRooms.setAdapter(factoryAdapter(R.array.ROOMS));
        estateFormBinding.etBedrooms.setAdapter(factoryAdapter(R.array.BEDROOMS));
        estateFormBinding.etBathrooms.setAdapter(factoryAdapter(R.array.BATHROOMS));
        estateFormBinding.etAgent.setAdapter(factoryAdapter(R.array.AGENT));
    }

    // for date picker
    private void setDateField() {
        estateFormBinding.upOfSaleDate.setOnClickListener(this);
        estateFormBinding.soldDate.setOnClickListener(this);
        //For up of sale date
        Calendar newCalendar = Calendar.getInstance();
        mUpOfSaleDateDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            estateFormBinding.upOfSaleDate.setText(mDateFormat.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        //For sold date
        mSoldDate = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            estateFormBinding.soldDate.setText(mDateFormat.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    // for click on date picker
    @Override
    public void onClick(View view) {
        if (view == estateFormBinding.upOfSaleDate) {
            mUpOfSaleDateDialog.show();
            mUpOfSaleDateDialog.getDatePicker().setMaxDate((Calendar.getInstance().getTimeInMillis()));

        } else if (view == estateFormBinding.soldDate) {
            mSoldDate.show();
            mSoldDate.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        }
    }

// for click on fab validate btn

    public void onClickValidateBtn() {

        estateFormBinding.validateFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> photoDescriptionList = new ArrayList<>();
                for (int i = 0; i < photoText.getPhotoDescription().size(); i++) {
                    AppCompatEditText editText = Objects.requireNonNull(Objects.requireNonNull(activityAddBinding.includeForm.rvPhoto.getLayoutManager()).findViewByPosition(i)).findViewById(R.id.photo_description);
                    String desc = Objects.requireNonNull(editText.getText()).toString();
                    photoDescriptionList.add(desc);
                }
                photoText.setPhotoDescription(photoDescriptionList);

//                fieldsRequired();
                saveEstates();

            }


//                Snackbar.make(v,"You're new Estate is created", Snackbar.LENGTH_SHORT).show();

//            }


        });
    }

//    public boolean fieldsRequired() {
//
//        String surfaceInput = estateFormBinding.inputSurface.getEditText().getText().toString();
//
//        if(surfaceInput.isEmpty()) {
//            estateFormBinding.etSurface.setError("Required");
//            return false;
//        } else {
//            estateFormBinding.etSurface.setText(null);
//            return true;
//        }
//    }



    //For save in database
    public void saveEstates() {

        Estate estate = new Estate(

                mandateNumberID,
                estateFormBinding.etEstate.getText().toString(),
                    Integer.parseInt(Objects.requireNonNull(estateFormBinding.etSurface.getText()).toString()),
                Integer.parseInt(estateFormBinding.etRooms.getText().toString()),
                Integer.parseInt(estateFormBinding.etBedrooms.getText().toString()),
                Integer.parseInt(estateFormBinding.etBathrooms.getText().toString()),
                Integer.parseInt(Objects.requireNonNull(estateFormBinding.etGround.getText()).toString()),
                 Double.parseDouble(Objects.requireNonNull((estateFormBinding.etPrice.getText())).toString()),
                Objects.requireNonNull(estateFormBinding.etDescription.getText()).toString(),
                Objects.requireNonNull(estateFormBinding.etAddress.getText()).toString(),
                Integer.parseInt(Objects.requireNonNull(estateFormBinding.etPostalCode.getText()).toString()),
                Objects.requireNonNull(estateFormBinding.etCity.getText()).toString(),
                estateFormBinding.boxSchools.isChecked(),
                estateFormBinding.boxStores.isChecked(),
                estateFormBinding.boxPark.isChecked(),
                estateFormBinding.boxRestaurants.isChecked(),
                estateFormBinding.availableRadiobtn.isChecked(),
                Objects.requireNonNull(estateFormBinding.upOfSaleDate.getText()).toString(),
                Objects.requireNonNull(estateFormBinding.soldDate.getText()).toString(),
                estateFormBinding.etAgent.getText().toString(),
                photo,
                photoText,
                video);




        this.estateViewModel.createEstate(estate);

    }


    //Configuring ViewModel
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);

//        this.estateViewModel.getPhotos().observe(this, this::updatePhotoList);
//        this.estateViewModel.getEstate().observe(this, new Observer<Estate>() {
//            @Override
//            public void onChanged(Estate estate) {
//
//
//            }
//        });
    }


    //For manage photos
    //For click on photo btn
    public void onClickPhotoBtn() {
        estateFormBinding.photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                methodRequiresTwoPermission();
                selectImage();
                saveImageInInternalStorage();

            }
        });
    }

    //For click on video btn
    public void onClickVideoBtn() {
        estateFormBinding.cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                methodRequiresTwoPermission();
                selectVideo();

            }
        });
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


                   Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (takePicture.resolveActivity(getPackageManager()) != null) {
                        //Create the File where the photo should go
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            Log.e("PhotoFileException", Objects.requireNonNull(ex.getMessage()));
                        }

                        //Continue only if the file was successfully created
                        if (photoFile != null) {

                                Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), "com.openclassrooms.realestatemanager.fileprovider", photoFile);

                                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                Log.d("PhotoUri", "photoUri =" + photoUri);


                                startActivityForResult(takePicture, 1);


                                        listPhoto.add(photoUri);
                                        photo.getPhotoList().add(String.valueOf(photoUri));
                                        photoText.getPhotoDescription().add("");
                                        adapter.setPhotoList(listPhoto);

                        }

                    }

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }


    protected File createImageFile() throws IOException {
        //Create an image file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy", Locale.FRANCE).format(new Date());
        String imageFileName = "JPEG" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /*prefix*/
                ".jpg", /*suffix*/
                storageDir /*directory*/
        );
//        Save file : path for use with ACTION_VIEW intent
        currentPhotoPath = image.getAbsolutePath();

        return image;
    }

    //For photos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE_CAMERA && data != null && data.getData() != null) {
                if (resultCode == Activity.RESULT_OK) {

                    selectedImage = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");

                    //For save in gallery
                    File file = new File(currentPhotoPath);
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uriCamera = Uri.fromFile(file);
                    mediaScanIntent.setData(uriCamera);
                    this.sendBroadcast(mediaScanIntent);


                }


            }
            if (requestCode == PICK_IMAGE_GALLERY && data != null && data.getData() != null) {
                if (resultCode == Activity.RESULT_OK) {

                    Uri contentUri = Objects.requireNonNull(data).getData();
                    String timeStamp = new SimpleDateFormat("ddMMyyyy", Locale.FRANCE).format(new Date());
                    String imageFileName = "JPEG" + timeStamp + "." + getFileExt(contentUri);
                    Log.d("Test uri gallery", "onActivityResult : Gallery Image Uri:" + imageFileName);

                    //For save image in internal storage
                    FileOutputStream fOut = null;
                    try {
                        fOut = openFileOutput("imageGallery", MODE_PRIVATE);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    OutputStreamWriter osw = new OutputStreamWriter(Objects.requireNonNull(fOut));
                    try {
                        osw.write(imageFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int len = imageFileName.length();
                    try {
                        osw.flush();
                        osw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    listPhoto.add(contentUri);
                    photo.getPhotoList().add(String.valueOf(contentUri));
                    photoText.getPhotoDescription().add("");
                    adapter.setPhotoList(listPhoto);


                }
            }
            if (requestCode == PICK_VIDEO_CAMERA && data != null && data.getData() != null) {
                if (resultCode == Activity.RESULT_OK) {
                    Uri contentURI = data.getData();
                    String recordedVideoPath = getPath(contentURI);
                    Log.d("recordedVideoPaht", recordedVideoPath);
//                saveVideoToInternalStorage(recordedVideoPath);
                    Objects.requireNonNull(activityAddBinding.includeForm.videoView).setVideoURI(contentURI);
                    activityAddBinding.includeForm.videoView.requestFocus();
                    MediaController mediaController = new MediaController(this);
                    activityAddBinding.includeForm.videoView.setMediaController(mediaController);
                    mediaController.setAnchorView(activityAddBinding.includeForm.videoView);
                    activityAddBinding.includeForm.videoView.start();
                    video.getPhotoList().add(String.valueOf(contentURI));
                }
            }
            if (requestCode == PICK_VIDEO_GALLERY && data != null && data.getData() != null) {
                if (resultCode == Activity.RESULT_OK) {

                    Uri contentURI = data.getData();

                    String selectedVideoPath = getPath(contentURI);
                    Log.d("path", selectedVideoPath);
//                    saveVideoToInternalStorage(selectedVideoPath);
                    Objects.requireNonNull(activityAddBinding.includeForm.videoView).setVideoURI(contentURI);
                    activityAddBinding.includeForm.videoView.requestFocus();
                    MediaController mediaController = new MediaController(this);
                    activityAddBinding.includeForm.videoView.setMediaController(mediaController);
                    mediaController.setAnchorView(activityAddBinding.includeForm.videoView);
                    activityAddBinding.includeForm.videoView.start();
                    video.getPhotoList().add(String.valueOf(contentURI));
                }
            }
        }
    }
    private String getFileExt(Uri contentUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(contentUri));
    }

    //for pick image camera
    public void saveImageInInternalStorage() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, "UniqueFileName" + ".jpg");
        if (!file.exists()) {
            Log.d("path", file.toString());
            FileOutputStream fos = null;
            try {
                if (selectedImage != null) {
                    fos = new FileOutputStream(file);
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    startActivityForResult(takeVideo, PICK_VIDEO_CAMERA);



                } else if (options[item].equals("Choose Video")) {
                    Intent pickVideo = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickVideo, PICK_VIDEO_GALLERY);


                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();

                }
            }
        });
        builderVideo.show();
    }

    //for video
    private void saveVideoToInternalStorage (String filePath) {

        File newfile;

        try {

            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY);
            newfile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4");

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            if(currentFile.exists()){

                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newfile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.v("vii", "Video file saved successfully.");
            }else{
                Log.v("vii", "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //For video
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = ((Cursor) cursor)
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


}






