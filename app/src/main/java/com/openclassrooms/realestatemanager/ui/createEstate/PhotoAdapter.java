package com.openclassrooms.realestatemanager.ui.createEstate;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder>{



private List<Integer> mViewColors;
private RequestManager glide;
//private List<PhotoList> mPhotoList;
//    private List<PhotoList> photoList;
private List<Uri> mPhotoList = new ArrayList<Uri>();


    public PhotoAdapter( List<Uri> listPhoto, RequestManager glide) {

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
    holder.updateWithDetails(this.mPhotoList.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public void setPhotoList(List<Uri> photos) {
        mPhotoList.clear();
        mPhotoList.addAll(photos);
        notifyDataSetChanged();
    }

    public Uri getPhoto (int position) {
        return mPhotoList.get(position);
    }
//    private void updatePhoto(List<PhotoList> photoList){
//        this.mPhotoList = mPhotoList;
//        this.notifyDataSetChanged();
//    }

//    public void updatePhoto(List<String> photoList) {
//    this.photoList = photoList;
//    this.notifyDataSetChanged();
//    }
}
