package com.openclassrooms.realestatemanager.ui;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;

import java.util.Objects;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private ActivityAddPhotoItemBinding activityAddPhotoItemBinding;
    private EstateViewModel estateViewModel;
    private Context context;
    private long estateEdit;


    public PhotoViewHolder(ActivityAddPhotoItemBinding activityAddPhotoItemBinding) {
        super(activityAddPhotoItemBinding.getRoot());
        this.activityAddPhotoItemBinding = activityAddPhotoItemBinding;



    }

    public void updateWithDetails(Uri photoList, RequestManager glide, String photoDescription, long estateEdit) {

            activityAddPhotoItemBinding.photoDescription.setText(photoDescription);

            glide.load(photoList).apply(RequestOptions.centerCropTransform()).into(activityAddPhotoItemBinding.photoImage);
                //for delete image display in Edit and Not in create
                if(estateEdit == 0){
            Objects.requireNonNull(activityAddPhotoItemBinding.deleteImage).setVisibility(View.INVISIBLE);
        } else {
                    Objects.requireNonNull(activityAddPhotoItemBinding.deleteImage).setVisibility(View.VISIBLE);
                }
    }
}
