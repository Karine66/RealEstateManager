package com.openclassrooms.realestatemanager.mainPage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.detailDescription.DetailFragment;
import com.openclassrooms.realestatemanager.listPage.ListFragment;
import com.openclassrooms.realestatemanager.utils.Utils;

public class MainActivity extends AppCompatActivity {

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
//        //First error id call error
////        this.textViewMain = findViewById(R.id.activity_second_activity_text_view_main);
         this.configureAndShowListFragment();
//         this.configureAndShowDetailFragment();
         this.configureToolbar();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void configureToolbar() {
         //Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
    }

    private void configureAndShowListFragment(){
        //Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);

        if (listFragment == null) {
            //Create new main fragment
            listFragment = new ListFragment();
            //Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, listFragment)
                    .commit();
        }
    }

//    private void configureAndShowDetailFragment(){
//        //Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
//        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
//
//        if (detailFragment == null) {
//            //Create new main fragment
//            detailFragment = new DetailFragment();
//            //Add it to FrameLayout container
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.frame_layout_main, detailFragment)
//                    .commit();
//        }
//    }
}
