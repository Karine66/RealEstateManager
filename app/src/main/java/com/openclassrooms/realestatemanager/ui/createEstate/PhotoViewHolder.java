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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

 private ActivityAddPhotoItemBinding activityAddPhotoItemBinding;
    private EstateViewModel estateViewModel;
    private Context context;


    public PhotoViewHolder(ActivityAddPhotoItemBinding activityAddPhotoItemBinding) {
        super(activityAddPhotoItemBinding.getRoot());
        this.activityAddPhotoItemBinding = activityAddPhotoItemBinding;


    }

    public void updateWithDetails(Uri photoList, RequestManager glide) {



        activityAddPhotoItemBinding.photoDescription.setText("");
//            glide.load(uriPhoto).apply(RequestOptions.centerCropTransform()).into(activityAddPhotoItemBinding.photoImage);

                glide.load(photoList).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("load failed", "Load failed", e);

                        // You can also log the individual causes:
                        for (Throwable t : Objects.requireNonNull(e).getRootCauses()) {
                            Log.e("Caused by", "Caused by", t);
                        }
                        // Or, to log all root causes locally, you can use the built in helper method:
                        e.logRootCauses("glide error");

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(activityAddPhotoItemBinding.photoImage);


    }
}
