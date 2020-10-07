package com.openclassrooms.realestatemanager.ui.searchPage;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentListItemBinding;
import com.openclassrooms.realestatemanager.models.Estate;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class SearchResultViewHolder extends RecyclerView.ViewHolder {

    FragmentListItemBinding fragmentListItemBinding;

    public SearchResultViewHolder (FragmentListItemBinding fragmentListItemBinding) {
        super(fragmentListItemBinding.getRoot());
        this.fragmentListItemBinding = fragmentListItemBinding;
    }

    @SuppressLint("SetTextI18n")
    public void updateWithEstate(Estate estate, RequestManager glide) {

        Objects.requireNonNull(fragmentListItemBinding.estateType).setText(estate.getEstateType());

        Objects.requireNonNull(fragmentListItemBinding.City).setText(estate.getCity());

        if (estate.getPrice() != null) {
            Objects.requireNonNull(fragmentListItemBinding.price).setText("$" + NumberFormat.getInstance(Locale.US).format(estate.getPrice()));


        }
        if (estate.getSold()) {
            fragmentListItemBinding.listPhotoSold.setImageResource(R.drawable.sold4);
        }

//        String photo = photoList.getPhotoList().get(0);

//        glide.load(photo).into(fragmentListItemBinding.listPhoto);
        if(!estate.getPhotoList().getPhotoList().isEmpty()) {
            glide.load(estate.getPhotoList().getPhotoList().get(0)).into(fragmentListItemBinding.listPhoto);
//
//        Log.d("photoList", "photolist" + estate.getPhotoList().getPhotoDescription().get(0));

        }else {
            fragmentListItemBinding.listPhoto.setImageResource(R.drawable.icon_no_image);
        }
    }
}