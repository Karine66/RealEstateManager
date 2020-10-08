package com.openclassrooms.realestatemanager.ui;

import android.content.Context;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private ActivityAddPhotoItemBinding activityAddPhotoItemBinding;
    private EstateViewModel estateViewModel;
    private Context context;


    public PhotoViewHolder(ActivityAddPhotoItemBinding activityAddPhotoItemBinding) {
        super(activityAddPhotoItemBinding.getRoot());
        this.activityAddPhotoItemBinding = activityAddPhotoItemBinding;



    }

    public void updateWithDetails(Uri photoList, RequestManager glide, String photoDescription) {

//

            activityAddPhotoItemBinding.photoDescription.setText(photoDescription);


            glide.load(photoList).apply(RequestOptions.centerCropTransform()).into(activityAddPhotoItemBinding.photoImage);

    }
}
