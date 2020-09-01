package com.openclassrooms.realestatemanager.ui.mapPage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityMapBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.geocodingAPI.Geocoding;
import com.openclassrooms.realestatemanager.models.geocodingAPI.Result;
import com.openclassrooms.realestatemanager.ui.BaseActivity;
import com.openclassrooms.realestatemanager.ui.createEstate.EstateViewModel;
import com.openclassrooms.realestatemanager.ui.detailDescription.DetailActivity;
import com.openclassrooms.realestatemanager.ui.detailDescription.DetailFragment;
import com.openclassrooms.realestatemanager.utils.EstateManagerStream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MapActivity extends BaseActivity implements OnMapReadyCallback, LocationListener, Serializable {

    private ActivityMapBinding activityMapBinding;
    protected static final int PERMS_CALL_ID = 200;
    private MapView mapView;
    private GoogleMap map;
    private String mPosition;
    private LocationManager locationManager;
    private GoogleMap googleMap;
    private Marker positionMarker;
    private Disposable mDisposable;
    private List<Result> resultGeocoding;
    private EstateViewModel estateViewModel;
    private String completeAddress;
    private List<Estate> estateList = new ArrayList<>();
    private List<String> adressList=new ArrayList<>();
    private String estateType;
    private Geocoding geocoding;
    private Estate est;
    private Long id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        configureViewModel();
        createStringForAddress(estateList);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        this.estateViewModel.getEstates().observe(this,this::createStringForAddress);

    }



    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);


    }

    /**
     * For permissions to position access
     */

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMS_CALL_ID);
            return;
        }

        locationManager = (LocationManager) (getSystemService(Context.LOCATION_SERVICE));

        if (Objects.requireNonNull(locationManager).isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 15000, 10, this);
            Log.e("GPSProvider", "testGPS");
        } else if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.PASSIVE_PROVIDER, 15000, 10, this);
            Log.e("PassiveProvider", "testPassive");
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 15000, 10, this);
            Log.e("NetWorkProvider", "testNetwork");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMS_CALL_ID) {
            checkPermissions();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }

    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
        Log.d("LocationProject", "Provider Enabled");
    }


    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Retrieve User location
     *
     * @param location
     */
    public void onLocationChanged(Location location) {
        double mLatitude = location.getLatitude();
        double mLongitude = location.getLongitude();

        if (map != null) {
            LatLng googleLocation = new LatLng(mLatitude, mLongitude);
            map.moveCamera(CameraUpdateFactory.newLatLng(googleLocation));
            mPosition = mLatitude + "," + mLongitude;
            Log.d("TestLatLng", mPosition);
            executeHttpRequestWithRetrofit(adressList);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        checkPermissions();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.zoomBy(15));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMS_CALL_ID);
            return;
        }

        googleMap.setMyLocationEnabled(true);

    }

    public void createStringForAddress(List<Estate> estateList) {

        if (!Objects.requireNonNull(estateList).isEmpty()) {
            for (Estate est : estateList) {
                 id = est.getMandateNumberID();
//               estateType = est.getEstateType();
                String address = est.getAddress();
                String postalCode = String.valueOf(est.getPostalCode());
                String city = est.getCity();
                completeAddress = address + "," + postalCode + "," + city;

                adressList.addAll(Collections.singleton(completeAddress));

                Log.d("adressList", "adressList"+ adressList);

                Log.d("createString", "createString" + completeAddress);

            }
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
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        .title(geo.getFormattedAddress()));


                positionMarker.showInfoWindow();
                positionMarker.setTag(id);
                Log.d("idMarker", String.valueOf(id));

//                Log.d("detailResultMap", String.valueOf(latLng));
//                Result markerResult = (Result) est.getMandateNumberID();
//              positionMarker.setTag(markerResult);

//            if (estateType.contains("House")) {
//                positionMarker = map.addMarker(new MarkerOptions().position(latLng)
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
//                        .title(geo.getFormattedAddress())
//                        .snippet(estateType));
//
//                positionMarker.showInfoWindow();
//
//                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                Log.d("detailResultMap", String.valueOf(latLng));
//            }
//                if (estateType.contains("Flat")) {
//                    positionMarker = map.addMarker(new MarkerOptions().position(latLng)
//                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
//                            .title(geo.getFormattedAddress())
//                    .snippet(estateType));
//
//                    positionMarker.showInfoWindow();
//                    positionMarker.setTag(estateType);
//                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                }
//                    if (estateType.contains("Duplex")) {
//                        positionMarker = map.addMarker(new MarkerOptions().position(latLng)
//                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
//                                .title(geo.getFormattedAddress())
//                        .snippet(estateType));
//                        positionMarker.showInfoWindow();
//                        positionMarker.setTag(estateType);
//
//                    } if (estateType.contains("Penthouse")) {
//                            positionMarker = map.addMarker(new MarkerOptions().position(latLng)
//                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
//                                    .title(geo.getFormattedAddress())
//                            .snippet(estateType));
//                            positionMarker.showInfoWindow();
//                            positionMarker.setTag(estateType);
//                        }
                    }
                }

    //http request for geocoding
    private void executeHttpRequestWithRetrofit(List<String> adressList) {
        this.mDisposable = EstateManagerStream.streamFetchGeocodeList(adressList)
                .subscribeWith(new DisposableObserver<Geocoding>() {

                    @Override
                    public void onNext(Geocoding geocoding) {

                        for (int i = 0; i < adressList.size(); i++) {

                            resultGeocoding = geocoding.getResults();

                        }
                    }

                    @Override
                    public void onComplete() {


                        for ( Result geo: resultGeocoding) {
                            map.clear();
                            LatLng latLng = new LatLng(geo.getGeometry().getLocation().getLat(),
                                    geo.getGeometry().getLocation().getLng()
                            );

                            positionMarker = map.addMarker(new MarkerOptions().position(latLng)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                                    .title(geo.getFormattedAddress()));


                            positionMarker.showInfoWindow();
                            positionMarker.setTag(id);
                            Log.d("idMarker", String.valueOf(id));

                            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {

                                    long estateId = Long.parseLong(Objects.requireNonNull(positionMarker.getTag()).toString());
                                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                    intent.putExtra("estateId",estateId);
                                    startActivity(intent);

                                }
                            });
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
