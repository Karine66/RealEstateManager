package com.openclassrooms.realestatemanager.ui.createEstate;

import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
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

           glide.load(photoList).apply(RequestOptions.centerCropTransform()).into(activityAddPhotoItemBinding.photoImage);


//            if(description!=null && !description.isEmpty()) {
//                activityAddPhotoItemBinding.photoDescription.setText(description);
//
//            }
//        activityAddPhotoItemBinding.photoDescription.setText(description);






    }
}
