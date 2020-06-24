package com.openclassrooms.realestatemanager.createEstate;

import androidx.appcompat.app.ActionBar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;

import com.openclassrooms.realestatemanager.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding;

import java.util.Arrays;
import java.util.Objects;

public class AddActivity extends BaseActivity {

    private ActivityAddBinding activityAddBinding;

    private AutoCompleteTextView dropdownEstate;
    private AutoCompleteTextView dropdownRooms;
    private AutoCompleteTextView dropdownBedrooms;
    private AutoCompleteTextView dropdownBathrooms;
    private AutoCompleteTextView dropdownAgent;
    private String [] ESTATES;
    private String[] ROOMS;
    private String[] BEDROOMS;
    private String[] BATHROOMS;
    private String[] AGENT;

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

//        this.dropDownString();
        this.dropDownView(view);
        this.dropDownAdapters();

//        this.dropDownEstates(view);
//        this.dropDownRooms(view);
//        this.dropDownBedrooms(view);
//        this.dropDownBathrooms(view);
//        this.dropDownAgentName(view);

    }




    public void dropDownAdapters() {

        ArrayAdapter <String> adapterEstates = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item,getResources().getStringArray(R.array.ESTATES));
        ArrayAdapter<String> adapterRooms = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.ROOMS));
        ArrayAdapter<String> adapterBedrooms = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.BEDROOMS));
        ArrayAdapter<String> adapterBathrooms = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.BATHROOMS));
        ArrayAdapter<String> adapterAgent = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, getResources().getStringArray(R.array.AGENT));
        dropdownEstate.setAdapter(adapterEstates);
        dropdownRooms.setAdapter(adapterRooms);
        dropdownBedrooms.setAdapter(adapterBedrooms);
        dropdownBathrooms.setAdapter(adapterBathrooms);
        dropdownAgent.setAdapter(adapterAgent);
    }

    public void dropDownView(View view) {
       dropdownEstate = view.findViewById(R.id.et_Estate);
       dropdownRooms = view.findViewById(R.id.et_rooms);
       dropdownBedrooms = view.findViewById(R.id.et_bedrooms);
       dropdownBathrooms = view.findViewById(R.id.et_bathrooms);
       dropdownAgent = view.findViewById(R.id.et_agent);
    }
    //For dropDown Estates
    public void dropDownEstates (View view) {

         String [] ESTATES = new String[] {"House", "Flat", "Duplex","Penthouse"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_menu_popup_item, ESTATES);
         AutoCompleteTextView editTextOutlinedExposedDropdown = view.findViewById(R.id.et_Estate);

        editTextOutlinedExposedDropdown.setAdapter(adapter);
    }

    //For dropDown number of rooms
    public void dropDownRooms (View view) {

        String [] ROOMS = new String[] {"1", "2", "3","4","5 et +"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_menu_popup_item, ROOMS);

        AutoCompleteTextView editTextOutlinedExposedDropdown = view.findViewById(R.id.et_rooms);
        editTextOutlinedExposedDropdown.setAdapter(adapter);
    }
    //For dropDown number of bedrooms
    public void dropDownBedrooms (View view) {

        String [] BEDROOMS = new String[] {"0","1", "2", "3","4","5 et +"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_menu_popup_item, BEDROOMS);

        AutoCompleteTextView editTextOutlinedExposedDropdown = view.findViewById(R.id.et_bedrooms);
        editTextOutlinedExposedDropdown.setAdapter(adapter);
    }
    //For dropDown number of bathrooms
    public void dropDownBathrooms (View view) {

        String [] BATHROOMS = new String[] {"1", "2", "3","4 et +"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_menu_popup_item, BATHROOMS);

        AutoCompleteTextView editTextOutlinedExposedDropdown = view.findViewById(R.id.et_bathrooms);
        editTextOutlinedExposedDropdown.setAdapter(adapter);
    }
    //For dropDown real estate agent name
    public void dropDownAgentName (View view) {

        String [] AGENT = new String[] {"Karine Danjard", "John Doe", "Octave Dupont","Eugene Martin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_menu_popup_item, AGENT);

        AutoCompleteTextView editTextOutlinedExposedDropdown = view.findViewById(R.id.et_agent);
        editTextOutlinedExposedDropdown.setAdapter(adapter);
    }

}