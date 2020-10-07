package com.openclassrooms.realestatemanager.ui.searchPage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentSearchResultBinding;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.SearchEstate;
import com.openclassrooms.realestatemanager.models.UriList;
import com.openclassrooms.realestatemanager.ui.EstateViewModel;
import com.openclassrooms.realestatemanager.ui.SearchViewModel;
import com.openclassrooms.realestatemanager.ui.listPage.ListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SearchResultFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private FragmentSearchResultBinding fragmentSearchResultBinding;

    private List<Estate> estateList;
    private UriList photoLists = new UriList();
    private SearchResultAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EstateViewModel estateViewModel;
    private SearchViewModel searchViewModel;
    private SearchEstate estateSearch;
    private ActionBar ab;

    public SearchResultFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SearchResultFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static SearchResultFragment newInstance(String param1, String param2) {
//        SearchResultFragment fragment = new SearchResultFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       //For viewBinding
        fragmentSearchResultBinding = FragmentSearchResultBinding.inflate(inflater, container,false);
        View view = fragmentSearchResultBinding.getRoot();

        configureViewModel();
        configureRecyclerView();

//        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getActionBar()).setSubtitle("Search Result");

        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //For change title action bar
//        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getActionBar()).setTitle("Search Results");
       
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);

        Intent intent = new Intent(Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent()));
        estateSearch = (SearchEstate) intent.getSerializableExtra("estateSearch");

        Log.d("estateSearch", String.valueOf(estateSearch));

        this.searchViewModel.searchEstate(Objects.requireNonNull(estateSearch.getEstateType()), estateSearch.getCity(),estateSearch.getMinRooms(), estateSearch.getMaxRooms(),
                estateSearch.getMinSurface(),estateSearch.getMaxSurface(), estateSearch.getMinPrice(), estateSearch.getMaxPrice(),
                estateSearch.getMinUpOfSaleDate(),estateSearch.getMaxOfSaleDate()).observe(this, this::updateEstateList);


    }



    /**
     * Configuration RecyclerView
     * Configure RecyclerView, Adapter, LayoutManager & glue it
     */
    private void configureRecyclerView() {

        this.estateList = new ArrayList<>();

        //Create adapter
        this.mAdapter = new SearchResultAdapter(this.estateList, Glide.with(this), this.photoLists);
        // Attach the adapter to the recyclerview
//       this.mRecyclerView.setAdapter(this.mAdapter);
//        Set Layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentSearchResultBinding.searchResultListRV.setLayoutManager(layoutManager);
        fragmentSearchResultBinding.searchResultListRV.setAdapter(mAdapter);
//       this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentSearchResultBinding = null;
    }

    private void updateEstateList(List<Estate> estates) {
        if(estates != null)
            mAdapter.updateData(estates);

    }
}