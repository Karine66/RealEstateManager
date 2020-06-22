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



    }
    //For dropDown Estates
    public void dropDownEstates (View view) {

        String [] ESTATES = new String[] {"House", "Flat", "Duplex", "Penthouse"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_menu_popup_item, ESTATES);

        AutoCompleteTextView editTextOutlinedExposedDropdown = view.findViewById(R.id.et_Estate);
        editTextOutlinedExposedDropdown.setAdapter(adapter);
    }




}