package com.openclassrooms.realestatemanager.ui.createEstate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;
import java.util.Objects;

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
