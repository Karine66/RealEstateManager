package com.openclassrooms.realestatemanager.createEstate;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding;
import com.openclassrooms.realestatemanager.databinding.EstateFormBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.PhotoList;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;


public class AddActivity extends BaseActivity implements View.OnClickListener {


    private static final int RC_CAMERA_AND_STORAGE =100;
    private static final String [] CAM_AND_READ_EXTERNAL_STORAGE =
        {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};


    private ActivityAddBinding activityAddBinding;
    private EstateFormBinding estateFormBinding;
    private DatePickerDialog mUpOfSaleDateDialog;
    private DatePickerDialog mSoldDate;
    private SimpleDateFormat mDateFormat;
    private Context context;

    private EstateViewModel estateViewModel;


    private PhotoAdapter adapter;
    private List<Integer> viewColors;
    private long mandateNumberID;
    private List<PhotoList> listPhoto;
    private RequestManager glide;


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
//        this.methodRequiresTwoPermission();
        this.onClickPhotoBtn();
        this.onClickVideoBtn();
        this.configureRecyclerView();
        this.setMandateID(mandateNumberID);

//        this.onClickGalleryBtn();
        //for title toolbar
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setTitle("Create Estate");
        //For date picker
        mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    }

    public void configureRecyclerView() {
//        ArrayList<Integer> viewColors = new ArrayList<>();
//        viewColors.add(Color.BLUE);
//        viewColors.add(Color.YELLOW);
//        viewColors.add(Color.MAGENTA);
//        viewColors.add(Color.RED);
//        viewColors.add(Color.BLACK);
          listPhoto = new ArrayList<>();

                adapter = new PhotoAdapter(listPhoto,glide);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        Objects.requireNonNull(estateFormBinding.rvPhoto).setLayoutManager(horizontalLayoutManager);
        estateFormBinding.rvPhoto.setAdapter(adapter);
    }
    private void updatePhoto(List<PhotoList> photoList){
        listPhoto.addAll(photoList);
        adapter.notifyDataSetChanged();
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
                 Double.parseDouble(Objects.requireNonNull(estateFormBinding.etPrice.getText()).toString()),
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
//              Objects.requireNonNull(estateFormBinding.soldDate.getText()).toString(),
                estateFormBinding.etAgent.getText().toString());

                this.estateViewModel.createEstate(estate);

    }
    //For retrieve automatically mandate number in edit text mandate number
    public void setMandateID(long mandateNumberID) {

        estateFormBinding.etMandate.setText(String.valueOf(mandateNumberID));
        estateFormBinding.etMandate.setEnabled(false);
    }

    //Configuring ViewModel
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
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


    //For bitmap
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:

                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
//                        Objects.requireNonNull(activityAddBinding.photoImage1).setImageBitmap(selectedImage);
//                            listPhoto.add(listPhoto.get(0));

                        Log.d("selectedImage", "selectedImage" +selectedImage);
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
//                                activityAddBinding.photoImage1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                listPhoto.add(BitmapFactory.decodeFile(picturePath));
                                Objects.requireNonNull(estateViewModel.getPhotos().getValue()).toString();
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




