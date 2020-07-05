package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.EstateDAO;
import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;

public class EstateDataRepository {

    private final EstateDAO estateDAO;

    public EstateDataRepository (EstateDAO estateDAO) {
        this.estateDAO = estateDAO;
    }
    //Get
    public LiveData<List<Estate>> getEstate(long mandateEstateID) {
        return this.estateDAO.getEstate(mandateEstateID);
    }
    //Create
    public void createEstate(Estate estate) {
        estateDAO.insertEstate(estate);
    }
    //Delete
    public void deleteEstate (long mandateEstateID) {
        estateDAO.deleteItem(mandateEstateID);
    }
    //Update
    public void updateEstate (Estate estate) {
        estateDAO.updateEstate(estate);
    }
}
