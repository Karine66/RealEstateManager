package com.openclassrooms.realestatemanager.createEstate;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;
import com.openclassrooms.realestatemanager.models.PhotoList;

import java.util.List;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

   ActivityAddPhotoItemBinding activityAddPhotoItemBinding;


    public PhotoViewHolder(ActivityAddPhotoItemBinding b) {
        super(b.getRoot());
        activityAddPhotoItemBinding = b;

    }

    public void updateWithDetails(PhotoList photoList,  RequestManager glide) {

        if(photoList != null)
            glide.load(photoList.getPhotoList()).into(activityAddPhotoItemBinding.photoImage);


    }
}
