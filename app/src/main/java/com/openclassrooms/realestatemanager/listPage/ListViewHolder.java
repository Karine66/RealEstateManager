package com.openclassrooms.realestatemanager.listPage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.databinding.FragmentListItemBinding;
import com.openclassrooms.realestatemanager.models.Estate;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class ListViewHolder extends RecyclerView.ViewHolder {


    FragmentListItemBinding fragmentListItemBinding;
//private Estate mEstate;

    public ListViewHolder(FragmentListItemBinding fragmentListItemBinding) {
        super(fragmentListItemBinding.getRoot());
        this.fragmentListItemBinding = fragmentListItemBinding;


    }

    public void updateWithEstate (Estate estate) {
//        mEstate = estate;
//        if (estate.getEstateType() != null && !estate.getEstateType().isEmpty()) {
          Objects.requireNonNull(fragmentListItemBinding.estateType).setText(estate.getEstateType());
//        } else {
//            Snackbar.make(fragmentListItemBinding.getRoot(), "No Estate type found", Snackbar.LENGTH_SHORT).show();
//        }
        if (estate.getCity() != null && !estate.getCity().isEmpty()) {
            Objects.requireNonNull(fragmentListItemBinding.City).setText(estate.getCity());
        } else {
            Snackbar.make(fragmentListItemBinding.getRoot(), "No city found", Snackbar.LENGTH_SHORT).show();
        }
        if (estate.getPrice() != null){
            Objects.requireNonNull(fragmentListItemBinding.price).setText(String.valueOf(estate.getPrice()));
        }else {
            Snackbar.make(fragmentListItemBinding.getRoot(), "No price found", Snackbar.LENGTH_SHORT).show();
        }

    }



}
