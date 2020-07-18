package com.openclassrooms.realestatemanager.createEstate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;
import com.openclassrooms.realestatemanager.models.PhotoList;

import java.util.Collections;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder>{

    ActivityAddPhotoItemBinding activityAddPhotoItemBinding;

private List<Integer> mViewColors;
private RequestManager glide;
private List<PhotoList> mPhotoList;


public PhotoAdapter(List<PhotoList> mPhotoList) {
    this.mPhotoList = mPhotoList;
}

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new PhotoViewHolder(ActivityAddPhotoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
//    int color = mViewColors.get(position);
//    holder.activityAddPhotoItemBinding.photoImage.setBackgroundColor(color);
    holder.updateWithDetails(Collections.singletonList(this.mPhotoList.get(position)));
    }

    @Override
    public int getItemCount() {
//        return mViewColors.size();
        return mPhotoList.size();
    }
}
