package com.openclassrooms.realestatemanager.listPage;

import android.annotation.SuppressLint;
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
            Objects.requireNonNull(fragmentListItemBinding.price).setText("$"+ NumberFormat.getInstance(Locale.US).format(estate.getPrice()));

        }


    }
}
