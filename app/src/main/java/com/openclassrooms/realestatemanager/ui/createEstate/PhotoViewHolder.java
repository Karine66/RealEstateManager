package com.openclassrooms.realestatemanager.ui.createEstate;

import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;
import com.openclassrooms.realestatemanager.models.PhotoList;

import java.io.File;
import java.util.Objects;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

 private ActivityAddPhotoItemBinding activityAddPhotoItemBinding;
    private EstateViewModel estateViewModel;


    public PhotoViewHolder(ActivityAddPhotoItemBinding activityAddPhotoItemBinding) {
        super(activityAddPhotoItemBinding.getRoot());
        this.activityAddPhotoItemBinding = activityAddPhotoItemBinding;

    }

    public void updateWithDetails(Uri photoList, RequestManager glide) {

//        Uri photoUri = Uri.fromFile( new File(String.valueOf(photoList.getPhotoList())));
//            glide.load(new File (Objects.requireNonNull(photoUri.getPath()))).into(activityAddPhotoItemBinding.photoImage);
        if (photoList != null) {
            glide.load(photoList).into(activityAddPhotoItemBinding.photoImage);
        }



    }
}
