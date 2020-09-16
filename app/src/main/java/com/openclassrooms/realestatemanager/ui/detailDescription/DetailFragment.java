package com.openclassrooms.realestatemanager.ui.detailDescription;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.databinding.FragmentDetailBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.PhotoDescription;
import com.openclassrooms.realestatemanager.models.UriList;
import com.openclassrooms.realestatemanager.models.geocodingAPI.Geocoding;
import com.openclassrooms.realestatemanager.models.geocodingAPI.Result;
import com.openclassrooms.realestatemanager.ui.EstateViewModel;
import com.openclassrooms.realestatemanager.ui.PhotoAdapter;
import com.openclassrooms.realestatemanager.utils.EstateManagerStream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

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
    private Disposable mDisposable;
    private String completeAddress;
    private List<Result> resultGeocoding;
    private Marker positionMarker;
    private long estateId;
    private DetailFragment detailFragment;
    private Serializable estateMap;
    private PhotoAdapter adapter;
    private List<Uri> listPhoto;
    private PhotoDescription photoText = new PhotoDescription();

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // For viewbinding
        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false);
        View view = fragmentDetailBinding.getRoot();
        updateUi();
        createStringForAddress();
        configureViewModel();
        configureRecyclerView();
        //for lite map
        GoogleMapOptions options = new GoogleMapOptions();
        options.liteMode(true);
        mapView = (MapView) fragmentDetailBinding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);

        if(estateMap!=null) {
            Intent intent = new Intent(Objects.requireNonNull(getActivity()).getIntent());
            Estate estateMap = (Estate) intent.getSerializableExtra("estate");
            estateId = Objects.requireNonNull(estateMap).getMandateNumberID();
            Log.d("idBundle", String.valueOf(estateMap));
        }
            this.estateViewModel.getEstate(estateId).observe(this, this::updateUIfromMarker);
        }

    public void configureRecyclerView() {

        listPhoto = new ArrayList<>();

        adapter = new PhotoAdapter(listPhoto, Glide.with(this), photoText.getPhotoDescription());
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        Objects.requireNonNull(fragmentDetailBinding.rvPhoto).setLayoutManager(horizontalLayoutManager);
        fragmentDetailBinding.rvPhoto.setAdapter(adapter);
        if (estate!= null &&!estate.getPhotoList().getPhotoList().isEmpty() && estate.getPhotoList().getPhotoList().size()>0) {
            for (String photoStr : estate.getPhotoList().getPhotoList()) {

                listPhoto.add(Uri.parse(photoStr));
            }

            adapter.setPhotoList(listPhoto);
            adapter.setPhotoDescription(estate.getPhotoDescription().getPhotoDescription());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private void updateUIfromMarker(Estate estate) {


        if (estate != null) {
            fragmentDetailBinding.etMandate.setText(String.valueOf(estate.getMandateNumberID()));
            fragmentDetailBinding.etMandate.setEnabled(false);
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
        }


    }




    @SuppressLint("SetTextI18n")
    public void updateUi() {
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        Estate estateDetail = (Estate) intent.getSerializableExtra("estate");
        Log.d("estateDetail", "estateDetail" + estateDetail);

        if (estateDetail != null) {
            fragmentDetailBinding.etMandate.setText(String.valueOf(estateDetail.getMandateNumberID()));
            fragmentDetailBinding.etMandate.setEnabled(false);
            fragmentDetailBinding.etSurface.setText(Objects.requireNonNull(estateDetail.getSurface()).toString());
            fragmentDetailBinding.etSurface.setEnabled(false);
            fragmentDetailBinding.etDescription.setText(estateDetail.getDescription());
            fragmentDetailBinding.etDescription.setEnabled(false);
            fragmentDetailBinding.etRooms.setText(Objects.requireNonNull(estateDetail.getRooms()).toString());
            fragmentDetailBinding.etRooms.setEnabled(false);
            fragmentDetailBinding.etBathrooms.setText(Objects.requireNonNull(estateDetail.getBathrooms()).toString());
            fragmentDetailBinding.etBathrooms.setEnabled(false);
            fragmentDetailBinding.etBedrooms.setText(Objects.requireNonNull(estateDetail.getBedrooms()).toString());
            fragmentDetailBinding.etBedrooms.setEnabled(false);
            fragmentDetailBinding.etAddress.setText(estateDetail.getAddress());
            fragmentDetailBinding.etAddress.setEnabled(false);
            fragmentDetailBinding.etPostalCode.setText(Objects.requireNonNull(estateDetail.getPostalCode()).toString());
            fragmentDetailBinding.etPostalCode.setEnabled(false);
            fragmentDetailBinding.etCity.setText(estateDetail.getCity());
            fragmentDetailBinding.etCity.setEnabled(false);

//        } else {
//            Snackbar.make(Objects.requireNonNull(getView()), "No Estate create", Snackbar.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    public void updateUiForTablet(Estate estate) {



        if (estate != null) {
            fragmentDetailBinding.etMandate.setText(String.valueOf(estate.getMandateNumberID()));
            fragmentDetailBinding.etMandate.setEnabled(false);
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
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
        googleMap.moveCamera(CameraUpdateFactory.zoomBy(15));
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMS_CALL_ID);
            return;
        }
        executeHttpRequestWithRetrofit();

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    public void createStringForAddress() {
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        Estate estateDetail = (Estate) intent.getSerializableExtra("estate");
        this.estate = estateDetail;
        Log.d("idDetail", "idDetail" + estateDetail);

        if(estateDetail != null) {
            String address = Objects.requireNonNull(estateDetail).getAddress();

            String postalCode = String.valueOf(estateDetail.getPostalCode());
            String city = estateDetail.getCity();
            completeAddress = address + "," + postalCode + "," + city;

            Log.d("createString", "createString" + completeAddress);
        }
    }

    //For retrieve estate position with LatLng and marker
    public void positionMarker(List<Result> resultGeocoding) {
        map.clear();
        for (Result geo : resultGeocoding) {

            LatLng latLng = new LatLng(geo.getGeometry().getLocation().getLat(),
                    geo.getGeometry().getLocation().getLng()
            );
            positionMarker = map.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            positionMarker.showInfoWindow();
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            Log.d("detailResultMap", String.valueOf(latLng));
        }

    }

    //http request for geocoding
    private void executeHttpRequestWithRetrofit() {
        this.mDisposable = EstateManagerStream.streamFetchGeocode(completeAddress)
                .subscribeWith(new DisposableObserver<Geocoding>() {

                    @Override
                    public void onNext(Geocoding geocoding) {

                        resultGeocoding = geocoding.getResults();
                    }

                    @Override
                    public void onComplete() {
                        if(completeAddress != null) {

                            positionMarker(resultGeocoding);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("onErrorGeocoding", Log.getStackTraceString(e));
                    }
                });
    }


    /**
     * Dispose subscription
     */
    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed())
            this.mDisposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }



}