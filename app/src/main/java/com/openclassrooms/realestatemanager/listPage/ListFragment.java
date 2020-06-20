package com.openclassrooms.realestatemanager.listPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.createEstate.AddActivity;
import com.openclassrooms.realestatemanager.databinding.FragmentListBinding;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;

import java.util.ArrayList;

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
    private ArrayList<Object> mEstateList;
    private ListAdapter mAdapter;
    private RecyclerView mRecyclerView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // For viewBinding
        fragmentListBinding = FragmentListBinding.inflate(inflater, container,false);
        View view = fragmentListBinding.getRoot();
        this.onClickFab();
//        this.configureRecyclerView();
//        this.configureOnClickRecyclerView();
        return view;
    }

    //For click on floating action button
    public void onClickFab () {
        fragmentListBinding.fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fabIntent = new Intent(getContext(), AddActivity.class);
                startActivity(fabIntent);
            }
        });

    }

/**
 * Configuration RecyclerView
 * Configure RecyclerView, Adapter, LayoutManager & glue it
 */
    private void configureRecyclerView() {

        this.mEstateList = new ArrayList<>();
        //Create adapter
        this.mAdapter = new ListAdapter(this.mEstateList);
        // Attach the adapter to the recyclerview
        this.mRecyclerView.setAdapter(this.mAdapter);
        //Set Layout manager
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Configure item click on RecyclerView
     */
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_list_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentListBinding = null;
    }


}