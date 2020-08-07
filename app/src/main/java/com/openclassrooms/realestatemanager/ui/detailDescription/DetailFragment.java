package com.openclassrooms.realestatemanager.ui.detailDescription;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentDetailBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.ui.createEstate.EstateViewModel;

import java.util.Collections;
import java.util.Objects;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link DetailFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class DetailFragment extends Fragment implements OnMapReadyCallback {

    protected static final int PERMS_CALL_ID = 200;
    private FragmentDetailBinding fragmentDetailBinding;
    private Estate estate;
    private EstateViewModel estateViewModel;
    private long mandateNumberID;
    private SupportMapFragment mapFragment;
    private MapView mapView;
    private GoogleMap map;
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public DetailFragment() {
        // Required empty public constructor
    }


//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DetailFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static DetailFragment newInstance(String param1, String param2) {
//        DetailFragment fragment = new DetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // For viewbinding
        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false);
        View view = fragmentDetailBinding.getRoot();
        configureViewModel();
        setMandateID(mandateNumberID);
        GoogleMapOptions options = new GoogleMapOptions();
        options.liteMode(true);
        mapView = (MapView) fragmentDetailBinding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        return view;

    }


    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);

        this.estateViewModel.getEstate().observe(this, this::updateUi);
    }

    @SuppressLint("SetTextI18n")
    public void updateUi(Estate estate) {

        if (estate != null) {
            fragmentDetailBinding.etSurface.setText(Objects.requireNonNull(estate.getSurface()).toString());
            fragmentDetailBinding.etSurface.setEnabled(false);
            fragmentDetailBinding.etDescription.setText(estate.getDescription());
            fragmentDetailBinding.etDescription.setEnabled(false);
            fragmentDetailBinding.etRooms.setText(Objects.requireNonNull(estate.getRooms()).toString());
            fragmentDetailBinding.etRooms.setEnabled(false);
            fragmentDetailBinding.etBathrooms.setText(Objects.requireNonNull(estate.getBathrooms()).toString());
            fragmentDetailBinding.etBathrooms.setEnabled(false);
            fragmentDetailBinding.etBedrooms.setText(Objects.requireNonNull(estate.getBedrooms()).toString());
            fragmentDetailBinding.etBedrooms.setEnabled(false);
            fragmentDetailBinding.etAddress.setText(estate.getAddress());
            fragmentDetailBinding.etAddress.setEnabled(false);
            fragmentDetailBinding.etPostalCode.setText(Objects.requireNonNull(estate.getPostalCode()).toString());
            fragmentDetailBinding.etPostalCode.setEnabled(false);
            fragmentDetailBinding.etCity.setText(estate.getCity());
            fragmentDetailBinding.etCity.setEnabled(false);
        } else {
            Snackbar.make(fragmentDetailBinding.getRoot(), "No Estate create", Snackbar.LENGTH_SHORT).show();
        }
    }

    //For retrieve automatically mandate number in edit text mandate number
    public void setMandateID(long mandateNumberID) {
        fragmentDetailBinding.etMandate.setText(String.valueOf(mandateNumberID));
        fragmentDetailBinding.etMandate.setEnabled(false);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
//        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//            }, PERMS_CALL_ID);
//            return;
//        }
//        map.setMyLocationEnabled(true);
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
}