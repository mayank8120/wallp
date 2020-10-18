package com.wallpaper.wallp;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wallpaper.wallp.Adapter.TrendAdapter;
import com.wallpaper.wallp.ModelClasses.TrendModel;

import java.util.ArrayList;

public class trendingfragment extends Fragment {

    DatabaseReference mref;
    ArrayList<TrendModel> arrayList;
    TrendAdapter trendAdapter;

    Context mcontext;


    RecyclerView recyclerViewtrend;

    public trendingfragment() {
        // Required empty public constructor
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_trending, container, false);

        recyclerViewtrend = root.findViewById(R.id.recyclerviewtrend);

        int spanCount = 2; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        recyclerViewtrend.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerViewtrend.setLayoutManager(layoutManager);
        recyclerViewtrend.setHasFixedSize(true);

        mref = FirebaseDatabase.getInstance().getReference();


        arrayList = new ArrayList<>();

        ClearAll();

        loaddatafromfirebase();

        return root;
    }


    private void loaddatafromfirebase() {

        Query query = mref.child("Wallpapers");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    TrendModel trendModel = new TrendModel();
                    trendModel.setWallpaperlink(snapshot1.child("wallpaperlink").getValue().toString());
                    trendModel.setCategory(snapshot1.child("category").getValue().toString());

                    arrayList.add(trendModel);

                }

                trendAdapter = new TrendAdapter(mcontext, arrayList);
                recyclerViewtrend.setAdapter(trendAdapter);
                trendAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void ClearAll() {
        if (arrayList != null) {
            arrayList.clear();

            if (trendAdapter != null) {
                trendAdapter.notifyDataSetChanged();
            }
        }
        arrayList = new ArrayList<>();
    }




}

