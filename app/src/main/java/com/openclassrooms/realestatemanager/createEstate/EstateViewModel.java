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



    public EstateViewModel (EstateDataRepository estateDataSource, Executor executor) {
        this.estateDataSource = estateDataSource;
        this.executor = executor;
    }

    public LiveData<List<Estate>> getEstate (long mandateNumberID) {
        return estateDataSource.getEstate(mandateNumberID);
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
}
