package com.openclassrooms.realestatemanager.ui.createEstate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
    private MutableLiveData<List<String>> mPhotos = new MutableLiveData<>();


    public EstateViewModel (EstateDataRepository estateDataSource, Executor executor) {
        this.estateDataSource = estateDataSource;
        this.executor = executor;
    }


    public LiveData<List<Estate>> getEstates () {
        return estateDataSource.getEstates();
    }
    public LiveData<Estate> getEstate (long mandateNumberID) {
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


    public MutableLiveData<List<String>> getPhotos () {
        return mPhotos;
    }


}
