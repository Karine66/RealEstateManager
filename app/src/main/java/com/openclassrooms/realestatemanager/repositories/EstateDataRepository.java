package com.openclassrooms.realestatemanager.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.database.dao.EstateDAO;
import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;
import java.util.Objects;

public class EstateDataRepository {

    private final EstateDAO estateDAO;

    public EstateDataRepository (EstateDAO estateDAO) {
        this.estateDAO = estateDAO;
    }
    //Get
    public LiveData<List<Estate>> getEstates() {
        return this.estateDAO.getEstates();
    }
    public LiveData<Estate> getEstate(long mandateNumberID) {
        return this.estateDAO.getEstate(mandateNumberID);
    }
    //Create
    public void createEstate(Estate estate) {
        try {
            estateDAO.insertEstate(estate);
        }catch (Exception e) {
            Log.e("Error insertEstate", Objects.requireNonNull(e.getMessage()));
        }

    }
    //Delete
    public void deleteEstate (long mandateEstateID) {
        estateDAO.deleteItem(mandateEstateID);
    }
    //Update
    public void updateEstate (Estate estate) {
        estateDAO.updateEstate(estate);
    }

    //For Search
    public LiveData<List<Estate>> getSearchEstate(SupportSQLiteQuery query) {
        return estateDAO.getSearchEstate(query);
    }
}
