package com.openclassrooms.realestatemanager.ui;

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



    private ActivityAddPhotoItemBinding activityAddPhotoItemBinding;


private RequestManager glide;
private List<Uri> mPhotoList = new ArrayList<Uri>();
private List<String> mPhotoDescription = new ArrayList<>();


    public PhotoAdapter(List<Uri> listPhoto,  RequestManager glide, ArrayList<String> photoDescription){
        this.glide = glide;
        this.mPhotoDescription = photoDescription;
}

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new PhotoViewHolder(ActivityAddPhotoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {

            try {

//         if(!mPhotoList.isEmpty() && !mPhotoDescription.isEmpty()){
                holder.updateWithDetails(this.mPhotoList.get(position), this.glide, this.mPhotoDescription.get(position));
            }catch (Exception e) {
                e.getMessage();
            }
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

    public void setPhotoDescription (List<String> photoDescription) {
        mPhotoDescription.clear();
        mPhotoDescription.addAll(photoDescription);
        notifyDataSetChanged();
    }




//    public void updatePhoto(List<String> photoList) {
//    this.photoList = photoList;
//    this.notifyDataSetChanged();
//    }
}
