package com.openclassrooms.realestatemanager.createEstate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EstateViewModel extends ViewModel {

    //Repository
    private final EstateDataRepository estateDataSource;
    private final Executor executor;

    private LiveData<List<Estate>> estate;



    public EstateViewModel (EstateDataRepository estateDataSource, Executor executor) {
        this.estateDataSource = estateDataSource;
        this.executor = executor;
    }


    public LiveData<List<Estate>> getEstates (long mandateNumberID) {
        return estateDataSource.getEstates(mandateNumberID);
    }
    public LiveData<Estate> getEstate () {
        return estateDataSource.getEstate();
    }

    public void createEstate(Estate estate) {
        executor.execute(()->{
            estateDataSource.createEstate(estate);
        });
    }

    public void deleteEstate(long mandateNumberID) {
        executor.execute(()-> {
            estateDataSource.deleteEstate(mandateNumberID);
        });
    }

    public void updateEstate(Estate estate) {
        executor.execute(()-> {
            estateDataSource.updateEstate(estate);
        });
    }


    public void createEstate(
            int mandateNumberID,
            String estateType,
            String surface,
            String rooms,
            String bedrooms,
            String bathrooms,
            String ground,
            String price,
            String description,
            String address,
            String postalCode,
            String city,
            boolean schools,
            boolean stores,
            boolean park,
            boolean restaurant,
            boolean available,
            String agentName) {

    }
}
