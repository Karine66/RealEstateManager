package com.openclassrooms.realestatemanager.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.ui.createEstate.EstateViewModel;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final EstateDataRepository estateDataSource;
    private final Executor executor;

    public ViewModelFactory (EstateDataRepository estateDataSource, Executor executor) {
        this.estateDataSource = estateDataSource;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(EstateViewModel.class)) {
        return (T) new EstateViewModel(estateDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
