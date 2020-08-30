package com.openclassrooms.realestatemanager.utils;

import android.location.Address;

import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.models.geocodingAPI.Geocoding;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EstateManagerService {

    String GOOGLE_MAP_API_KEY = BuildConfig.GOOGLE_MAP_API_KEY;

    //Geocoding API Request
    @GET("maps/api/geocode/json?key="+GOOGLE_MAP_API_KEY)
    Observable<Geocoding> getGeocode (@Query("address") String address);

    //Geocoding API Request
    @GET("maps/api/geocode/json?key="+GOOGLE_MAP_API_KEY)
    Observable<Geocoding> getGeocodeList (@Query("address") List<String> address);
}
