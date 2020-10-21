package com.openclassrooms.realestatemanager.ui.searchPage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import com.openclassrooms.realestatemanager.ui.listPage.ListFragment;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchResultActivity extends BaseActivity {

    private ActivitySearchResultBinding activitySearchResultBinding;
    private SearchResultFragment searchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchResultBinding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        View view = activitySearchResultBinding.getRoot();

        setContentView(view);

        this.configureToolbar();
        this.configureUpButton();
        this.configureAndShowSearchResultFragment();

        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setTitle("Search Results");

    }




    private void configureAndShowSearchResultFragment(){
//        Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        searchResultFragment = (SearchResultFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_search_result);

        if (searchResultFragment == null) {
            //Create new main fragment
            searchResultFragment = new SearchResultFragment();
            //Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_search_result, searchResultFragment)
                    .commit();
        }
    }

}