package com.openclassrooms.realestatemanager.ui.searchPage;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.databinding.FragmentListItemBinding;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.UriList;
import com.openclassrooms.realestatemanager.ui.listPage.ListViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    //For data
    private List<Estate> estateList;
    private RequestManager glide;
    private List<UriList> photoLists;

    //constructor
    public SearchResultAdapter(List<Estate> estateList, RequestManager glide, UriList photoLists) {

        this.estateList = new ArrayList<>();
        this.glide = glide;
        this.photoLists = new ArrayList<>();
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SearchResultViewHolder(FragmentListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        holder.updateWithEstate(this.estateList.get(position), this.glide);

    }

    @Override
    public int getItemCount() {
        return this.estateList.size();
    }

    public Estate getEstates (int position) {
        return this.estateList.get(position);
    }

    public void updateData(List<Estate> estateList) {
        this.estateList = estateList;
        this.notifyDataSetChanged();
    }


}

