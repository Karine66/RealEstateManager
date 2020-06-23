package com.openclassrooms.realestatemanager.createEstate;

import androidx.appcompat.app.ActionBar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.openclassrooms.realestatemanager.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding;

import java.util.Objects;

public class AddActivity extends BaseActivity {

    private ActivityAddBinding activityAddBinding;

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

        this.dropDownEstates(view);
        this.dropDownRooms(view);
        this.dropDownBedrooms(view);
        this.dropDownBathrooms(view);
        this.dropDownAgentName(view);



    }
    //For dropDown Estates
    public void dropDownEstates (View view) {

        String [] ESTATES = new String[] {"House", "Flat", "Duplex", "Penthouse"};
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