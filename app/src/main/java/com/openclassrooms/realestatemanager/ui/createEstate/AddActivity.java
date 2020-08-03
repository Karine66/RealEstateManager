package com.openclassrooms.realestatemanager.ui.createEstate;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.database.converters.PhotoListConverter;
import com.openclassrooms.realestatemanager.models.PhotoList;
import com.openclassrooms.realestatemanager.ui.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding;
import com.openclassrooms.realestatemanager.databinding.EstateFormBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class AddActivity extends BaseActivity implements View.OnClickListener {

    private static final int CAMERA_PERM_CODE = 100;
//    private static final int CAMERA_REQUEST_CODE = 200;
//    private static final int GALLERY_REQUEST_CODE = 300;

    private static final int RC_CAMERA_AND_STORAGE =100;
    private static final String [] CAM_AND_READ_EXTERNAL_STORAGE =
        {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_TAKE_PHOTO = 200;


    private ActivityAddBinding activityAddBinding;
    private EstateFormBinding estateFormBinding;
    private DatePickerDialog mUpOfSaleDateDialog;
    private DatePickerDialog mSoldDate;
    private SimpleDateFormat mDateFormat;
    private View view;
    private Context context;

    private EstateViewModel estateViewModel;


    private PhotoAdapter adapter;
    private List<Integer> viewColors;
    private long mandateNumberID;
    private List<String> listPhoto;
    private RequestManager glide;
    private Bitmap selectedImage;
    private String currentPhotoPath;
    private List<String> photolist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for view binding
        activityAddBinding = ActivityAddBinding.inflate(getLayoutInflater());
        estateFormBinding = activityAddBinding.includeForm;
        View view = activityAddBinding.getRoot();
        setContentView(view);

        this.configureToolbar();
        this.configureUpButton();
        this.dropDownAdapters();
        this.setDateField();
        this.onClickValidateBtn();
        this.configureViewModel();
        this.onClickPhotoBtn();
        this.onClickVideoBtn();
        this.configureRecyclerView();

        //for title toolbar
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setTitle("Create Estate");
        //For date picker
        mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        //For mandate number
        estateFormBinding.etMandate.setVisibility(View.INVISIBLE);
        estateFormBinding.inputMandate.setVisibility(View.INVISIBLE);

    }


    public void configureRecyclerView() {

          listPhoto = new ArrayList<>();

          adapter = new PhotoAdapter(listPhoto, Glide.with(this));
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        Objects.requireNonNull(estateFormBinding.rvPhoto).setLayoutManager(horizontalLayoutManager);
        estateFormBinding.rvPhoto.setAdapter(adapter);
    }
//        private void updatePhotoList(List<String> photoList) {
//        if(photoList != null)
//            adapter.updatePhoto(photoList);
//        adapter.notifyDataSetChanged();
//    }

//    private void updatePhotoList(List<PhotoList> photoList){
//        listPhoto.addAll(photoList);
//        adapter.notifyDataSetChanged();
//    }

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

               saveEstates();



//                Snackbar.make(v,"You're new Estate is created", Snackbar.LENGTH_SHORT).show();

            }


        });
    }

    //For save in database
    public void saveEstates() {


        Estate estate = new Estate(

                 Long.parseLong(Objects.requireNonNull(estateFormBinding.etMandate.getText()).toString()),
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
                estateFormBinding.etAgent.getText().toString());

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
             methodRequiresTwoPermission();
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
                methodRequiresTwoPermission();
                selectVideo();

            }
        });
    }
    //For photos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_CAMERA && data != null && data.getData() != null) {
            if (resultCode == Activity.RESULT_OK) {

                File file = new File(currentPhotoPath);
//                Objects.requireNonNull(estateFormBinding.cameraView).setImageURI(Uri.fromFile(file));
                selectedImage = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");

                //For save in gallery
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                Log.d("TestUri", "Uri image is" + contentUri);
                String contentUriToString = contentUri.toString();
//                estateViewModel.getPhotos().setValue(Collections.singletonList(contentUriToString));
//                updatePhoto(photolist);
            }
        }
        if (requestCode == PICK_IMAGE_GALLERY && data != null && data.getData() != null) {
            if (resultCode == Activity.RESULT_OK) {

                Uri contentUri = Objects.requireNonNull(data).getData();
                String timeStamp = new SimpleDateFormat("ddMMyyyy", Locale.FRANCE).format(new Date());
                String imageFileName = "JPEG" + timeStamp + "." + getFileExt(contentUri);
                Log.d("Test uri gallery", "onActivityResult : Gallery Image Uri:" + imageFileName);
//                Objects.requireNonNull(estateFormBinding.cameraView).setImageURI(contentUri);
                estateViewModel.getPhotos().setValue(Collections.singletonList(imageFileName));
//                updatePhotoList(photolist);
                //For save image in internal storage
                FileOutputStream fOut = null;
                try {
                    fOut = openFileOutput("imageGallery",MODE_PRIVATE);
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

//                listPhoto.add(contentUri);
//                Log.d("listPhotoGallery", "listPhotoGallery" + listPhoto);
            }
        }
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(contentUri));
    }

    public void saveImageInInternalStorage () {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, "UniqueFileName" + ".jpg");
        if (!file.exists()) {
            Log.d("path", file.toString());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    }






