package com.openclassrooms.realestatemanager.ui.listPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentListBinding;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.ui.createEstate.EstateViewModel;

import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.ui.detailDescription.DetailActivity;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ListFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentListBinding fragmentListBinding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Estate> estateList;
    private ListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EstateViewModel estateViewModel;
    private long mandateNumberID;
    private EstateDataRepository estateDateRepository;

    public ListFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ListFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ListFragment newInstance(String param1, String param2) {
//        ListFragment fragment = new ListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // For viewBinding
        fragmentListBinding = FragmentListBinding.inflate(inflater, container,false);
        View view = fragmentListBinding.getRoot();
        this.configureViewModel();
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        return view;


    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);

        this.estateViewModel.getEstates().observe(this, this::updateEstateList);


    }



    /**
 * Configuration RecyclerView
 * Configure RecyclerView, Adapter, LayoutManager & glue it
 */
    private void configureRecyclerView() {

        this.estateList = new ArrayList<>();
        //Create adapter
        this.mAdapter = new ListAdapter(this.estateList);
        // Attach the adapter to the recyclerview
//        this.mRecyclerView.setAdapter(this.mAdapter);
        //Set Layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentListBinding.fragmentListRV.setLayoutManager(layoutManager);
        fragmentListBinding.fragmentListRV.setAdapter(mAdapter);
//        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Configure item click on RecyclerView
     */
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(fragmentListBinding.fragmentListRV, R.layout.fragment_list_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Intent intent = new Intent(getContext(), DetailActivity.class);
//                            Estate estate = mAdapter.getEstate(position);
//                            Log.d("Test click Rv", "click on" + estate.getPrice());
                            startActivity(intent);

                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentListBinding = null;
    }

    private void updateEstateList(List<Estate> estates) {
        if(estates != null)
            mAdapter.updateData(estates);

    }





}