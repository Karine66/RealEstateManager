package com.openclassrooms.realestatemanager.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.UriList;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class SearchViewModel extends ViewModel {

    //Repository
    private final EstateDataRepository estateDataSource;
    private final Executor executor;
    //Constructor
    public SearchViewModel (EstateDataRepository estateDataSource, Executor executor) {
        this.estateDataSource = estateDataSource;
        this.executor = executor;
    }

    public LiveData<Estate> searchEstate (

            String estateType,
            String city,
            Integer minRooms,
            Integer maxRooms,
            Integer minSurface,
            Integer maxSurface,
            Double minPrice,
            Double maxPrice,
            String minUpOfSaleDate,
            String maxUpOfSaleDate
//        UriList minPhotos,
//        UriList maxPhotos,
//        Boolean schools,
//        Boolean stores,
//        Boolean park,
//        Boolean restaurants,
//        Boolean sold

        ) {
            String queryString = "";
            List<Object> args = new ArrayList<>();
            boolean containsCondition = false;

            queryString += "SELECT * FROM Estate";

            if (!estateType.isEmpty()) {
                queryString += "AND estateType=?";
                args.add(estateType);
                containsCondition = true;
            }
            if (!city.isEmpty()) {
                queryString += "AND city=?";
                args.add(city);
                containsCondition = true;
            }
            if (minRooms >= 0 && maxRooms > 0) {
                queryString += "AND rooms BETWEEN ? AND ?";
                args.add(minRooms);
                args.add(maxRooms);
                containsCondition = true;
            }
            if (minSurface >= 0 && maxSurface > 0) {
                queryString += "AND surface BETWEEN ? AND ?";
                args.add(minSurface);
                args.add(maxSurface);
                containsCondition = true;
            }
            if (minPrice >= 0 && maxPrice > 0) {
                queryString += "AND price BETWEEN ? AND ?";
                args.add(minPrice);
                args.add(maxPrice);
                containsCondition = true;
            }
            if (!minUpOfSaleDate.isEmpty() && !maxUpOfSaleDate.isEmpty()) {
                queryString += "AND upOfSaleDate BETWEEN ? AND ?";
                args.add(minUpOfSaleDate);
                args.add(maxUpOfSaleDate);
            }
//        if(!minPhotos.getPhotoList().isEmpty() && !maxPhotos.getPhotoList().isEmpty()) {
//            queryString += "AND photos BETWEEN ? AND ?";
//            args.add(minPhotos);
//            args.add(maxPhotos);
//        }

        return this.searchEstate(estateType, city, minRooms, maxRooms, minSurface, maxSurface, minPrice, maxPrice, minUpOfSaleDate, maxUpOfSaleDate);
    }

}
