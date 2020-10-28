package com.openclassrooms.realestatemanager.ui.detailDescription;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.databinding.FragmentDetailBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.PhotoDescription;
import com.openclassrooms.realestatemanager.models.geocodingAPI.Geocoding;
import com.openclassrooms.realestatemanager.models.geocodingAPI.Result;
import com.openclassrooms.realestatemanager.ui.EstateViewModel;
import com.openclassrooms.realestatemanager.ui.PhotoAdapter;
import com.openclassrooms.realestatemanager.utils.EstateManagerStream;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class DetailFragment extends Fragment implements OnMapReadyCallback {

    protected static final int PERMS_CALL_ID = 200;
    private FragmentDetailBinding fragmentDetailBinding;
    private Estate estate;
    private EstateViewModel estateViewModel;
    private MapView mapView;
    private GoogleMap map;
    private Disposable mDisposable;
    private String completeAddress;
    private List<Result> resultGeocoding;
    private Marker positionMarker;
    private long estateId;
    private Serializable estateMap;
    private PhotoAdapter adapter;
    private List<Uri> listPhoto;
    private PhotoDescription photoText = new PhotoDescription();
    private long estateEdit;
    private Estate estateDetail;
    private long estateDetailId;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // For viewbinding
        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false);
        View view = fragmentDetailBinding.getRoot();

//        updateUi(estate);
        createStringForAddress();
        configureViewModel();
        configureRecyclerView();

        //for lite map
        GoogleMapOptions options = new GoogleMapOptions();
        options.liteMode(true);
        mapView = (MapView) fragmentDetailBinding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        //For videos
        fragmentDetailBinding.videoView.requestFocus();
        MediaController mediaController = new MediaController(getContext());
        fragmentDetailBinding.videoView.setMediaController(mediaController);
        mediaController.setAnchorView(fragmentDetailBinding.videoView);
        fragmentDetailBinding.videoView.start();

//                if (estateDetail != null) {
//            Intent intent = Objects.requireNonNull(getActivity()).getIntent();
//            Estate estateDetail = (Estate) intent.getSerializableExtra("estate");
//            estateDetailId = estateDetail.getMandateNumberID();
//            Log.d("estateDetailId", "estateDetailId" + estateDetailId);

//        this.estateViewModel.getEstate(estateDetailId).observe(this, this::updateUi);


        return view;
    }

    /**
     * Configure ViewModel
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);

        this.estateViewModel.getEstate(estateDetailId).observe(this, this::updateUi);
        //For retrieve data
        if (estateMap != null) {
            Intent intent = new Intent(Objects.requireNonNull(getActivity()).getIntent());
            Estate estateMap = (Estate) intent.getSerializableExtra("estate");
            estateId = Objects.requireNonNull(estateMap).getMandateNumberID();
            Log.d("idBundle", String.valueOf(estateMap));

            this.estateViewModel.getEstate(estateId).observe(this, this::updateUIfromMarker);
        }
        }



    /**
     * For RecyclerView photos
     */
    public void configureRecyclerView() {

        listPhoto = new ArrayList<>();

        adapter = new PhotoAdapter(listPhoto, Glide.with(this), photoText.getPhotoDescription(), estateEdit);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        Objects.requireNonNull(fragmentDetailBinding.rvPhoto).setLayoutManager(horizontalLayoutManager);
        fragmentDetailBinding.rvPhoto.setAdapter(adapter);
        //for photos
        if (estate != null && !estate.getPhotoList().getPhotoList().isEmpty() && estate.getPhotoList().getPhotoList().size() > 0) {
            for (String photoStr : estate.getPhotoList().getPhotoList()) {

                listPhoto.add(Uri.parse(photoStr));
            }
            adapter.setPhotoList(listPhoto);
            adapter.setPhotoDescription(estate.getPhotoDescription().getPhotoDescription());
        }
    }

    /**
     * For retrieve Estate from marker Map
     *
     * @param estate
     */
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

            if (!estate.getVideo().getPhotoList().isEmpty() && estate.getVideo().getPhotoList().size() > 0) {
                for (String videoStr : estate.getVideo().getPhotoList()) {

                    fragmentDetailBinding.videoView.setVideoURI(Uri.parse(videoStr));
                }
            }
        }
    }

    /**
     * For update UI from list
     */
    @SuppressLint("SetTextI18n")
    public void updateUi(Estate estate) {
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        Estate estateDetail = (Estate) intent.getSerializableExtra("estate");
        long estateDetailId = estateDetail.getMandateNumberID();
//        this.estateViewModel.getEstate(estateDetailId).observe(this, this::updateUi);
//        Log.d("estateDetail", "estateDetail" + estateDetail);

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

            if (!estateDetail.getVideo().getPhotoList().isEmpty() && estateDetail.getVideo().getPhotoList().size() > 0) {
                for (String videoStr : estateDetail.getVideo().getPhotoList()) {

                    fragmentDetailBinding.videoView.setVideoURI(Uri.parse(videoStr));
                }
            }
        }
        this.estateViewModel.getEstate(estateDetailId).observe(this, this::updateUi);

    }

    /**
     * For edit button tablet in MainActivity
     * @return
     */
    public Estate getEstate() {
        return estate;
    }

    /**
     * For update UI for tablet
     *
     * @param estate
     */
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

            listPhoto.clear();
            if (!estate.getPhotoList().getPhotoList().isEmpty()) {
                for (String photoStr : estate.getPhotoList().getPhotoList()) {
                    listPhoto.add(Uri.parse(photoStr));
                }
                adapter.setPhotoList(listPhoto);
                adapter.setPhotoDescription(estate.getPhotoDescription().getPhotoDescription());
            }
            if (!estate.getVideo().getPhotoList().isEmpty() && estate.getVideo().getPhotoList().size() > 0) {
                for (String videoStr : estate.getVideo().getPhotoList()) {

                    fragmentDetailBinding.videoView.setVideoURI(Uri.parse(videoStr));
                }
            }
        }
    }

    /**
     * For display map
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (Utils.isInternetAvailable(Objects.requireNonNull(getContext()))) {
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
        } else {
            Snackbar.make(fragmentDetailBinding.getRoot(), "No internet available", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    /**
     * For create string adress for geocoding API
     */
    public void createStringForAddress() {
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        Estate estateDetail = (Estate) intent.getSerializableExtra("estate");
        this.estate = estateDetail;
        Log.d("idDetail", "idDetail" + estateDetail);

        if (estateDetail != null) {
            String address = Objects.requireNonNull(estateDetail).getAddress();

            String postalCode = String.valueOf(estateDetail.getPostalCode());
            String city = estateDetail.getCity();
            completeAddress = address + "," + postalCode + "," + city;

            Log.d("createString", "createString" + completeAddress);
        }
    }

    /**
     * For retrieve estate position with LatLng and marker
     *
     * @param resultGeocoding
     */
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

    /**
     * RX Java http request for geocoding API
     */
    private void executeHttpRequestWithRetrofit() {
        this.mDisposable = EstateManagerStream.streamFetchGeocode(completeAddress)
                .subscribeWith(new DisposableObserver<Geocoding>() {

                    @Override
                    public void onNext(Geocoding geocoding) {

                        resultGeocoding = geocoding.getResults();
                    }

                    @Override
                    public void onComplete() {
                        if (completeAddress != null) {

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