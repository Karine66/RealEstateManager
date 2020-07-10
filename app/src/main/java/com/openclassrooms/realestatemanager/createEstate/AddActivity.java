package com.openclassrooms.realestatemanager.createEstate;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding;
import com.openclassrooms.realestatemanager.databinding.EstateFormBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for view binding
        activityAddBinding = ActivityAddBinding.inflate(getLayoutInflater());
        View view = activityAddBinding.getRoot();
        setContentView(view);

//        // data to populate the RecyclerView with
//        ArrayList<Integer> viewColors = new ArrayList<>();
//        viewColors.add(Color.BLUE);
//        viewColors.add(Color.YELLOW);
//        viewColors.add(Color.MAGENTA);
//        viewColors.add(Color.RED);
//        viewColors.add(Color.BLACK);

        //For toolbar
        this.configureToolbar();
        this.configureUpButton();
        this.dropDownAdapters();
        this.setDateField();
        this.onClickValidateBtn();
        this.onClickPhotoBtn();
        this.configureViewModel();
        this.configureRecyclerView();

//        this.onClickGalleryBtn();
        //for title toolbar
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setTitle("Create Estate");
        //For date picker
        mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    }

    public void configureRecyclerView() {
        ArrayList<Integer> viewColors = new ArrayList<>();
        viewColors.add(Color.BLUE);
        viewColors.add(Color.YELLOW);
        viewColors.add(Color.MAGENTA);
        viewColors.add(Color.RED);
        viewColors.add(Color.BLACK);

                adapter = new PhotoAdapter(viewColors);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        Objects.requireNonNull(activityAddBinding.rvPhoto).setLayoutManager(horizontalLayoutManager);
        activityAddBinding.rvPhoto.setAdapter(adapter);
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


//                Snackbar.make(v,"You're new Estate is created", Snackbar.LENGTH_SHORT).show();
            }


        });
    }


//    public void createEstates() {
//        estateViewModel.createEstate(
//                activityAddBinding.etMandate.getId(),
//                activityAddBinding.etEstate.getText().toString(),
//                Objects.requireNonNull(activityAddBinding.etSurface.getText()).toString(),
//                activityAddBinding.etRooms.getText().toString(),
//                activityAddBinding.etBedrooms.getText().toString(),
//                activityAddBinding.etBathrooms.getText().toString(),
//                Objects.requireNonNull(activityAddBinding.etGround.getText()).toString(),
//                Objects.requireNonNull(activityAddBinding.etPrice.getText()).toString(),
//                Objects.requireNonNull(activityAddBinding.etDescription.getText()).toString(),
//                Objects.requireNonNull(activityAddBinding.etAddress.getText()).toString(),
//                Objects.requireNonNull(activityAddBinding.etPostalCode.getText()).toString(),
//                Objects.requireNonNull(activityAddBinding.etCity.getText()).toString(),
//                activityAddBinding.boxSchools.isChecked(),
//                activityAddBinding.boxStores.isChecked(),
//                activityAddBinding.boxPark.isChecked(),
//                activityAddBinding.boxRestaurants.isChecked(),
//                activityAddBinding.availableRadiobtn.isChecked(),
//                Objects.requireNonNull(activityAddBinding.upOfSaleDate.getText()).toString(),
//                Objects.requireNonNull(activityAddBinding.soldDate.getText()).toString(),
//                activityAddBinding.etAgent.getText().toString()
//        );
//
//    }



    //Configuring ViewModel
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
        this.estateViewModel.getEstate().observe(this, new Observer<Estate>() {
            @Override
            public void onChanged(Estate estate) {

            }
        });
    }
    //For manage photos
    //For click on photo btn
    public void onClickPhotoBtn() {
     activityAddBinding.photoBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            methodRequiresTwoPermission();
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




