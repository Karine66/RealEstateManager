package com.openclassrooms.realestatemanager.createEstate;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import com.openclassrooms.realestatemanager.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class AddActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener{

    private ActivityAddBinding activityAddBinding;

    private AutoCompleteTextView dropdownEstate;
    private AutoCompleteTextView dropdownRooms;
    private AutoCompleteTextView dropdownBedrooms;
    private AutoCompleteTextView dropdownBathrooms;
    private AutoCompleteTextView dropdownAgent;


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
        //for title toolbar
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setTitle("Create Estate");

        this.dropDownAdapters();

        activityAddBinding.upOfSaleDate.setOnClickListener(this);
        activityAddBinding.soldDate.setOnClickListener(this);


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

    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.upOfSaleDate:
            case R.id.soldDate:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                break;

        }
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        String currentDateStringSold = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        activityAddBinding.upOfSaleDate.setText(currentDateString);
        activityAddBinding.soldDate.setText(currentDateStringSold);
    }

}