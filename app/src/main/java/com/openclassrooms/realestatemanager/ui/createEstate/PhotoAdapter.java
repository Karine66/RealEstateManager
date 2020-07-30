package com.openclassrooms.realestatemanager.ui.createEstate;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;
import com.openclassrooms.realestatemanager.models.PhotoList;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder>{



private List<Integer> mViewColors;
private RequestManager glide;
private List<PhotoList> mPhotoList;
    private List<PhotoList> photoList;


    public PhotoAdapter(List<PhotoList> mPhotoList, RequestManager glide) {
    this.mPhotoList = mPhotoList;
    this.glide = glide;
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
    holder.updateWithDetails(mPhotoList.get(position), glide);
    }

    @Override
    public int getItemCount() {
//        return mViewColors.size();
        return mPhotoList.size();
    }

//    private void updatePhoto(List<PhotoList> photoList){
//        this.mPhotoList = mPhotoList;
//        this.notifyDataSetChanged();
//    }

    public void updatePhoto(List<PhotoList> photoList) {
    this.photoList = photoList;
    this.notifyDataSetChanged();
    }
}