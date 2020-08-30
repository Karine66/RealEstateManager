package com.openclassrooms.realestatemanager.ui.detailDescription;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddBinding;
import com.openclassrooms.realestatemanager.databinding.ActivityDetailBinding;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.ui.BaseActivity;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.Objects;

public class DetailActivity extends BaseActivity {

    private ActivityDetailBinding activityDetailBinding;
    private DetailFragment detailFragment;
    private Bundle bundle;
    private Estate estate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = activityDetailBinding.getRoot();
        setContentView(view);

        this.configureToolbar();
        this.configureUpButton();
        this.configureAndShowDetailFragment();

        //for title toolbar
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setTitle("Estate Description");

        //for retrieve estate from marker
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();


        if (!Utils.isInternetAvailable(this)) {
            Snackbar.make(activityDetailBinding.getRoot(), "No internet",Snackbar.LENGTH_SHORT).show();
        }


    }



        //for detailFragment
        private void configureAndShowDetailFragment(){
        //Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment_frameLayout);

        if (detailFragment == null) {
            //Create new main fragment
            detailFragment = new DetailFragment();
            //Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment_frameLayout, detailFragment)
                    .commit();
        }
    }
}