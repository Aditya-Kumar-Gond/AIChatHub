package com.example.aichathub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashboardFragment extends Fragment {

    RecyclerView rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        rv = view.findViewById(R.id.rv);
        DataAdapter adapter = new DataAdapter();
      //  StaggeredGridLayoutManager manager2 = new StaggeredGridLayoutManager(view.getContext(),)
        GridLayoutManager manager = new GridLayoutManager(view.getContext(),2);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);



        return view;
    }
}