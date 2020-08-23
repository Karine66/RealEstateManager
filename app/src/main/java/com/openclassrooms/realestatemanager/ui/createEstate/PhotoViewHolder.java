package com.openclassrooms.realestatemanager.ui.createEstate;

import android.content.Context;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

 private ActivityAddPhotoItemBinding activityAddPhotoItemBinding;
    private EstateViewModel estateViewModel;
    private Context context;


    public PhotoViewHolder(ActivityAddPhotoItemBinding activityAddPhotoItemBinding) {
        super(activityAddPhotoItemBinding.getRoot());
        this.activityAddPhotoItemBinding = activityAddPhotoItemBinding;


    }

    public void updateWithDetails(Uri photoList, RequestManager glide) {
//        if(!TextUtils.isEmpty(String.valueOf( photoList))) {
//            Uri photoUri = Uri.fromFile(new File(String.valueOf(photoList)));
//            glide.load(new File(Objects.requireNonNull(photoUri.getPath()))).into(activityAddPhotoItemBinding.photoImage);
//        }
//            if(!TextUtils.isEmpty(String.valueOf( photoList))) {
//                glide.load(photoList.getPath()).apply(RequestOptions.centerCropTransform()).into(activityAddPhotoItemBinding.photoImage);
//            }





        glide.load(photoList).apply(RequestOptions.centerCropTransform()).into(activityAddPhotoItemBinding.photoImage);






    }
}
