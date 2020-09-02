package com.openclassrooms.realestatemanager.ui.detailDescription;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityDetailBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.ui.BaseActivity;
import com.openclassrooms.realestatemanager.ui.createEstate.EstateViewModel;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.List;
import java.util.Objects;

public class DetailActivity extends BaseActivity {

    private ActivityDetailBinding activityDetailBinding;
    private DetailFragment detailFragment;
    private Bundle bundle;
    private Estate estate;
    private long estateId;
    private EstateViewModel estateViewModel;
    private List<Estate> estateList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = activityDetailBinding.getRoot();
        setContentView(view);

        this.configureToolbar();
        this.configureUpButton();
        this.configureAndShowDetailFragment();
        this.configureViewModel();

//        this.retrieveDataMap();
        //for title toolbar
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setTitle("Estate Description");


       


//        if (!Utils.isInternetAvailable(this)) {
//            Snackbar.make(activityDetailBinding.getRoot(), "No internet",Snackbar.LENGTH_SHORT).show();
//        }


    }
//    //for retrieve data marker
//    private void retrieveDataMap() {
//
//        this.getIntent().getLongExtra("estateId", estateId);
//
//        Log.d("idBundle", String.valueOf(bundle));
//    }

    @Override
    public void onResume() {
        super.onResume();
        // Call update method here because we are sure that DetailFragment is visible
        this.updateDetailUiForFragment();
    }
    //Configuring ViewModel
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
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

    private void updateDetailUiForFragment() {

        Intent intentTablet = Objects.requireNonNull(this).getIntent();
        estate = (Estate) intentTablet.getSerializableExtra("estate");
        Log.d("estateDetail", "estateDetail" + estate);

    }
}