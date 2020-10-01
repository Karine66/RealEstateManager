package com.openclassrooms.realestatemanager.ui.searchPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivitySearchBinding;
import com.openclassrooms.realestatemanager.databinding.ActivitySearchResultBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.UriList;
import com.openclassrooms.realestatemanager.ui.BaseActivity;
import com.openclassrooms.realestatemanager.ui.EstateViewModel;
import com.openclassrooms.realestatemanager.ui.detailDescription.DetailActivity;
import com.openclassrooms.realestatemanager.ui.detailDescription.DetailFragment;
import com.openclassrooms.realestatemanager.ui.listPage.ListAdapter;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchResultActivity extends BaseActivity {

    private ActivitySearchResultBinding activitySearchResultBinding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchResultBinding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        View view = activitySearchResultBinding.getRoot();
        setContentView(view);



    }



}