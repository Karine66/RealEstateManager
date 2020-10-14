package com.openclassrooms.realestatemanager.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.UriList;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.utils.Utils;

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

    public LiveData<List<Estate>> searchEstate (

            String estateType,
            String city,
            Integer minRooms,
            Integer maxRooms,
            Integer minSurface,
            Integer maxSurface,
            Double minPrice,
            Double maxPrice,
            Long minUpOfSaleDate,
            Long maxUpOfSaleDate,
            Boolean photos,
        Boolean schools
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
                if(containsCondition) {
                    queryString += " AND";
                } else {
                    queryString += " WHERE";
                    containsCondition = true;
                } queryString += " estateType=?";
                  args.add(estateType);
            }
//                queryString += "AND estateType=?";
//                args.add(estateType);
//                containsCondition = true;
//            }
            if (!city.isEmpty()) {
                if(containsCondition) {
                    queryString += " AND";
                }else {
                    queryString += " WHERE";
                    containsCondition = true;
                } queryString += " city=?";
                args.add(city);
//                containsCondition = true;
            }
            if (minRooms !=null && maxRooms!=null && minRooms >= 0 && maxRooms > 0) {
                if(containsCondition){
                    queryString += " AND";
                }else {
                    queryString += " WHERE";
                    containsCondition = true;
                } queryString += " rooms BETWEEN ? AND ?";
//                queryString += "AND rooms BETWEEN ? AND ?";
                args.add(minRooms);
                args.add(maxRooms);
//                containsCondition = true;
            }
            if (minSurface!=null && maxSurface!=null && minSurface >= 0 && maxSurface > 0) {
                if(containsCondition){
                    queryString += " AND";
                }else {
                    queryString += " WHERE";
                }queryString += " surface BETWEEN ? AND ?";
                args.add(minSurface);
                args.add(maxSurface);
//                containsCondition = true;
            }
            if (minPrice!=null && maxPrice!=null && minPrice >= 0 && maxPrice > 0) {
                if(containsCondition) {
                    queryString += " AND";
                } else {
                    queryString += " WHERE";
                } queryString += " price BETWEEN ? AND ?";
                args.add(minPrice);
                args.add(maxPrice);
//                containsCondition = true;
            }
            if (minUpOfSaleDate !=null && maxUpOfSaleDate!= null && minUpOfSaleDate >=0 && maxUpOfSaleDate >0) {
                if(containsCondition) {
                    queryString += " AND";
                } else {
                    queryString += " WHERE";
                } queryString += " upOfSaleDate BETWEEN ? AND ?";
                args.add(minUpOfSaleDate);
                args.add(maxUpOfSaleDate);
//                containsCondition = true;
            }


        if(photos.equals(true)) {
                if(containsCondition) {
                    queryString += " AND";
                }else {
                    queryString += " WHERE";
                } queryString += " photoList <> ''";
                args.add(true);
            }

            if(schools.equals(true)) {
                if(containsCondition) {
                    queryString += " AND";
                }else{
                    queryString += " WHERE";
                } queryString += " schools = 1";
                    args.add(true);
            }
        Log.d("queryString", "queryString" + queryString);
        return estateDataSource.getSearchEstate(queryString, args);


    }

}
