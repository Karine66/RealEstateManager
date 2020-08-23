package com.openclassrooms.realestatemanager.ui.createEstate;

import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPhotoItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder>{

private ActivityAddPhotoItemBinding activityAddPhotoItemBinding;


private RequestManager glide;
private List<Uri> mPhotoList = new ArrayList<Uri>();
private List<String> mPhotoDescription = new ArrayList<>();


    public PhotoAdapter(List<Uri> listPhoto, RequestManager glide, ArrayList<String> mPhotoDescription) {

    this.glide = glide;

//    ArrayAdapter<String> adapterEstates = new ArrayAdapter<String>(context, R.layout.dropdown_menu_popup_item,
//            .getStringArray(R.array.DESCRIPTION);

}

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new PhotoViewHolder(ActivityAddPhotoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {

//   holder.updateWithDetails(this.mPhotoList.get(position), this.glide, this.mPhotoDescription.get(position));
       holder.updateWithDetails(this.mPhotoList.get(position), this.glide);
//       activityAddPhotoItemBinding.photoDescription.setText(mPhotoDescription.get(holder.getAdapterPosition()));

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
