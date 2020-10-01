package com.openclassrooms.realestatemanager.ui.searchPage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivitySearchBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.SearchEstate;
import com.openclassrooms.realestatemanager.ui.BaseActivity;
import com.openclassrooms.realestatemanager.ui.EstateViewModel;
import com.openclassrooms.realestatemanager.ui.SearchViewModel;
import com.openclassrooms.realestatemanager.ui.createAndEditEstate.AddEditActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel searchViewModel;
    private SearchEstate estateSearch;
    private DatePickerDialog mUpOfSaleDateMinDialog;
    private SimpleDateFormat mDateFormat;
    private DatePickerDialog mUpOfSaleDateMaxDialog;
    private String estateType;
    private String city;
    private int minRooms;
    private int maxRooms;
    private int minSurface;
    private int maxSurface;
    private double minPrice;
    private double maxPrice;
    private String minUpOfSaleDate;
    private String maxUpOfSaleDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = activitySearchBinding.getRoot();
        setContentView(view);
        this.dropDownAdapters();
        this.setDateField();
        this.configureViewModel();

        this.onClickValidateBtn();
//        this.showSearchEstate();

        //For date picker
        mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        //for toolbar
//        ActionBar ab = getSupportActionBar();
//        Objects.requireNonNull(ab).setTitle("Search Estate");
    }

    //for adapter generic
    private ArrayAdapter<String> factoryAdapter(int resId) {
        return new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, getResources().getStringArray(resId));
    }

    //For adapters dropdown
    public void dropDownAdapters() {
        activitySearchBinding.etEstate.setAdapter(factoryAdapter(R.array.ESTATES));
    }

    // for date picker
    private void setDateField() {
        activitySearchBinding.etUpOfSaleDateMini.setOnClickListener(this);
        activitySearchBinding.etUpOfSaleDateMaxi.setOnClickListener(this);
        //For up of sale date min
        Calendar newCalendar = Calendar.getInstance();
        mUpOfSaleDateMinDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            activitySearchBinding.etUpOfSaleDateMini.setText(mDateFormat.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        //For up of sale date max
        mUpOfSaleDateMaxDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            activitySearchBinding.etUpOfSaleDateMaxi.setText(mDateFormat.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    // for click on date picker
    @Override
    public void onClick(View view) {
        if (view == activitySearchBinding.etUpOfSaleDateMini) {
            mUpOfSaleDateMinDialog.show();
            mUpOfSaleDateMinDialog.getDatePicker().setMaxDate((Calendar.getInstance().getTimeInMillis()));

        } else if (view == activitySearchBinding.etUpOfSaleDateMaxi) {
            mUpOfSaleDateMaxDialog.show();
            mUpOfSaleDateMaxDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        }
    }


    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);

    }

    // for click on fab validate btn
    public void onClickValidateBtn() {

        activitySearchBinding.validateFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSearchEstate();
                Intent fabIntent = new Intent(getApplicationContext(), SearchResultActivity.class);
                fabIntent.putExtra("estateSearch", estateSearch);
                startActivity(fabIntent);
//                showSearchEstate();
                Log.d("SaveSearch", "saveSearch"+estateSearch);

            }
        });
    }


    public void showSearchEstate() {

        estateSearch = new SearchEstate();

        if(!activitySearchBinding.etEstate.getText().toString().isEmpty()) {
            estateType = activitySearchBinding.etEstate.getText().toString();
            estateSearch.setEstateType(estateType);
        }
        if(!Objects.requireNonNull(activitySearchBinding.etCity.getText()).toString().isEmpty()) {
            city = Objects.requireNonNull(activitySearchBinding.etCity.getText()).toString();
            estateSearch.setCity(city);
        }
        if(!Objects.requireNonNull(activitySearchBinding.etRoomsMin.getText()).toString().isEmpty()) {
            minRooms = Integer.parseInt(Objects.requireNonNull(activitySearchBinding.etRoomsMin.getText()).toString());
            estateSearch.setMinRooms(minRooms);
        }
        if(!Objects.requireNonNull(activitySearchBinding.etRoomsMax.getText()).toString().isEmpty()) {
            maxRooms = Integer.parseInt(Objects.requireNonNull(activitySearchBinding.etRoomsMax.getText()).toString());
            estateSearch.setMaxRooms(maxRooms);
        }
        if(!activitySearchBinding.etSurfaceMini.getText().toString().isEmpty()) {
            minSurface = Integer.parseInt(Objects.requireNonNull(activitySearchBinding.etSurfaceMini.getText()).toString());
            estateSearch.setMinSurface(minSurface);
        }
        if(!activitySearchBinding.etSurfaceMaxi.getText().toString().isEmpty()) {
            maxSurface = Integer.parseInt(Objects.requireNonNull(activitySearchBinding.etSurfaceMaxi.getText()).toString());
            estateSearch.setMaxSurface(maxSurface);
        }
        if(!Objects.requireNonNull(activitySearchBinding.etPriceMini.getText()).toString().isEmpty()) {
            minPrice = Double.parseDouble(Objects.requireNonNull(activitySearchBinding.etPriceMini.getText()).toString());
            estateSearch.setMinPrice(minPrice);
        }
        if(!Objects.requireNonNull(activitySearchBinding.etPriceMaxi.getText()).toString().isEmpty()) {
            maxPrice = Double.parseDouble(Objects.requireNonNull(activitySearchBinding.etPriceMaxi.getText()).toString());
            estateSearch.setMaxPrice(maxPrice);
        }
        if(!Objects.requireNonNull(activitySearchBinding.etUpOfSaleDateMini.getText()).toString().isEmpty()) {
            minUpOfSaleDate = Objects.requireNonNull(activitySearchBinding.etUpOfSaleDateMini.getText()).toString();
            estateSearch.setMinUpOfSaleDate(minUpOfSaleDate);
        }
        if(!Objects.requireNonNull(activitySearchBinding.etUpOfSaleDateMaxi.getText()).toString().isEmpty()) {
            maxUpOfSaleDate = Objects.requireNonNull(activitySearchBinding.etUpOfSaleDateMaxi.getText()).toString();
            estateSearch.setMaxOfSaleDate(maxUpOfSaleDate);
        }
      Log.d("estateSearch", "estateSearch" + estateSearch);

//        this.searchViewModel.searchEstate(estateType, city, minRooms, maxRooms, minSurface,maxSurface,minPrice,maxPrice,minUpOfSaleDate,maxUpOfSaleDate);

    }
}