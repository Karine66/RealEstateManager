package com.openclassrooms.realestatemanager.ui.listPage;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentListItemBinding;
import com.openclassrooms.realestatemanager.models.Estate;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class ListViewHolder extends RecyclerView.ViewHolder {


    FragmentListItemBinding fragmentListItemBinding;
//private Estate mEstate;

    public ListViewHolder(FragmentListItemBinding fragmentListItemBinding) {
        super(fragmentListItemBinding.getRoot());
        this.fragmentListItemBinding = fragmentListItemBinding;



    }

    @SuppressLint("SetTextI18n")
    public void updateWithEstate (Estate estate) {

        Objects.requireNonNull(fragmentListItemBinding.estateType).setText(estate.getEstateType());

        Objects.requireNonNull(fragmentListItemBinding.City).setText(estate.getCity());

        if (estate.getPrice() != null) {
            Objects.requireNonNull(fragmentListItemBinding.price).setText("$" + NumberFormat.getInstance(Locale.US).format(estate.getPrice()));


        }
        if (estate.getSold()) {
            fragmentListItemBinding.listPhotoSold.setImageResource(R.drawable.sold2);
        }

    }
    }

