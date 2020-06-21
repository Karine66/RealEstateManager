package com.openclassrooms.realestatemanager.createEstate;

import androidx.appcompat.app.ActionBar;

import android.os.Bundle;
import android.view.View;

import com.openclassrooms.realestatemanager.BaseActivity;
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
        this.configureToolbar();
        this.configureUpButton();

        //for title toolbar
        ActionBar ab = getSupportActionBar();
       Objects.requireNonNull(ab).setTitle("Create Estate");

    }

}