package com.openclassrooms.realestatemanager.ui.mainPage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.openclassrooms.realestatemanager.ui.BaseActivity;
import com.openclassrooms.realestatemanager.ui.EditEstate.EditActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.createEstate.AddActivity;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.ui.detailDescription.DetailFragment;
import com.openclassrooms.realestatemanager.ui.listPage.ListFragment;
import com.openclassrooms.realestatemanager.ui.mapPage.MapActivity;
import com.openclassrooms.realestatemanager.ui.searchPage.SearchActivity;


public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private ListFragment listFragment;
    private DetailFragment detailFragment;

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
//         this.configureAndShowDetailFragment();
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

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
         //Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    //For click on floating action button
    public void onClickFab () {
            binding.fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fabIntent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(fabIntent);
            }
        });

    }

    //for click on buttons in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         //Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.edit_btn :
                Intent editIntent = new Intent(this, EditActivity.class);
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

//    private void configureAndShowDetailFragment(){
//        //Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
//        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment_frameLayout);
//
//        if (detailFragment == null) {
//            //Create new main fragment
//            detailFragment = new DetailFragment();
//            //Add it to FrameLayout container
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.detail_fragment_frameLayout, detailFragment)
//                    .commit();
//        }
//    }
}
