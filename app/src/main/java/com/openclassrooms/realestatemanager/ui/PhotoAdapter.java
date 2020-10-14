package com.openclassrooms.realestatemanager.ui;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;
import com.openclassrooms.realestatemanager.models.Estate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> implements View.OnClickListener {



    private ActivityAddPhotoItemBinding activityAddPhotoItemBinding;


private RequestManager glide;
private List<Uri> mPhotoList = new ArrayList<Uri>();
private List<String> mPhotoDescription = new ArrayList<>();
private EstateViewModel estateViewModel;
    private long estateEdit;

    public PhotoAdapter(List<Uri> listPhoto,  RequestManager glide, ArrayList<String> photoDescription, long estateEdit){
        this.glide = glide;
        this.mPhotoDescription = photoDescription;
       this.estateEdit = estateEdit;
}

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Objects.requireNonNull(activityAddPhotoItemBinding.deleteImage).setOnClickListener(this);
        return new PhotoViewHolder(ActivityAddPhotoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {


        Uri photoUri = null;
        String photoDescription ="";
        if(mPhotoList.size()>position) {
            photoUri = mPhotoList.get(position);
        }
        if(mPhotoDescription.size()>position) {
            photoDescription = mPhotoDescription.get(position);
        }
            try {
                holder.updateWithDetails(photoUri, this.glide, photoDescription);
            }catch (Exception e) {
                e.getMessage();
            }

//            if(estateEdit == 0) {
//              Objects.requireNonNull(activityAddPhotoItemBinding.deleteImage).setVisibility(View.INVISIBLE);
//
//
//            }
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

    private void deletePhoto (Estate estate) {
        this.estateViewModel.deleteEstate(estate.getMandateNumberID());
    }


    @Override
    public void onClick(View v) {

    }


}
