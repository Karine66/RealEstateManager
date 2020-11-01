package com.openclassrooms.realestatemanager.ui.mainPage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.ui.BaseActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.EstateViewModel;
import com.openclassrooms.realestatemanager.ui.createAndEditEstate.AddEditActivity;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.ui.detailDescription.DetailActivity;
import com.openclassrooms.realestatemanager.ui.detailDescription.DetailFragment;
import com.openclassrooms.realestatemanager.ui.listPage.ListFragment;
import com.openclassrooms.realestatemanager.ui.mapPage.MapActivity;
import com.openclassrooms.realestatemanager.ui.searchPage.SearchActivity;

import java.util.Objects;


public class MainActivity extends BaseActivity {


    private ActivityMainBinding binding;
    private ListFragment listFragment;
   private DetailFragment detailFragment;
   private DetailActivity detailActivity;
    private Estate estate;
    private EstateViewModel estateViewModel;
    private long mandateNumberID;
    private long idEstate;
    private Estate tabletMain;
    private Estate estateMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        this.onClickFab();
//        //First error id call error
////        this.textViewMain = findViewById(R.id.activity_second_activity_text_view_main);
         this.configureAndShowListFragment();
        this.configureAndShowDetailFragment();
        this.configureViewModel();

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             this.configureToolbar();

         }



     }

//    @SuppressLint("SetTextI18n")
//    private void configureTextViewQuantity(){
//        int quantity = Utils.convertDollarToEuro(100);
//        this.textViewQuantity.setTextSize(20);
//        //Second Error : call integer in setText
//        //this.texViewQuantity.setText(quantity);
//        this.textViewQuantity.setText(Integer.toString(quantity));
//    }

    /**
     * Configure ViewModel
     */
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }
    /**
     * For menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
         //Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    /**
     * For click on fab for create new estate
     */
    public void onClickFab () {
            binding.fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent fabIntent = new Intent(getApplicationContext(), AddEditActivity.class);
                    startActivity(fabIntent);
            }
        });
    }

    /**
     * For click on button on toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         //Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.edit_btn :

//                   idEstate = detailFragment.getEstate().getMandateNumberID();
                Intent intent = Objects.requireNonNull(getIntent());
                Estate estateDetail = (Estate) intent.getSerializableExtra("estate");
                idEstate = Objects.requireNonNull(estateDetail).getMandateNumberID();

                    Intent editIntent = new Intent(this, AddEditActivity.class);
                    editIntent.putExtra("iDEstate", idEstate);
                    startActivity(editIntent);
                return true;
            case R.id.search_btn :
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case R.id.map_btn :
                Intent mapIntent = new Intent(this, MapActivity.class);
                startActivity(mapIntent);
                return true;
            default :
                return
                        super.onOptionsItemSelected(item);
        }
    }

    /**
     * For connecting fragment list
     */
    private void configureAndShowListFragment(){
//        Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment_frameLayout);

        if (listFragment == null) {
            //Create new main fragment
            listFragment = new ListFragment();
            //Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list_fragment_frameLayout, listFragment)
                    .commit();
        }
    }

    /**
     * For connecting fragment detail
     */
    private void configureAndShowDetailFragment(){
        //Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment_frameLayout);

        if (detailFragment == null && findViewById(R.id.detail_fragment_frameLayout) !=null) {
            //Create new main fragment
            detailFragment = new DetailFragment();
            //Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment_frameLayout, detailFragment)
                    .commit();
        }
    }
}
