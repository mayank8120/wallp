package com.wallpaper.wallp;



import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wallpaper.wallp.Adapter.CollectionsAdapter;
import com.wallpaper.wallp.ModelClasses.CollectionsModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class collections_fragment extends Fragment {


    DatabaseReference mref;
    ArrayList<CollectionsModel> arrayList;
    CollectionsAdapter collectionsAdapter;

    Context mcontext;


    RecyclerView recyclerViewcollection;

    public collections_fragment() {
        // Required empty public constructor
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_trending, container, false);

        recyclerViewcollection= root.findViewById(R.id.recyclerviewtrend);

        /*int spanCount = 2; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        recyclerViewtrend.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewcollection.setLayoutManager(layoutManager);
        recyclerViewcollection.setHasFixedSize(true);

        mref = FirebaseDatabase.getInstance().getReference();


        arrayList = new ArrayList<>();

        ClearAll();

        loaddatafromfirebase();

        return root;
    }


    private void loaddatafromfirebase() {

        Query query = mref.child("CategoryList");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    /*TrendModel trendModel = new TrendModel();
                    trendModel.setWallpaperlink(snapshot1.child("wallpaperlink").getValue().toString());
                    trendModel.setCategory(snapshot1.child("category").getValue().toString());

                    arrayList.add(trendModel);*/

                    CollectionsModel collectionsModel=new CollectionsModel();
                    collectionsModel.setWallpaperlink(snapshot1.child("wallpaperlink").getValue().toString());
                    collectionsModel.setCategory(snapshot1.child("category").getValue().toString());
                    arrayList.add(collectionsModel);

                }

                /*trendAdapter = new TrendAdapter(mcontext, arrayList);
                recyclerViewtrend.setAdapter(trendAdapter);
                trendAdapter.notifyDataSetChanged();*/

                collectionsAdapter =new CollectionsAdapter(mcontext,arrayList);
                recyclerViewcollection.setAdapter(collectionsAdapter);
                collectionsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void ClearAll() {
        if (arrayList != null) {
            arrayList.clear();

            if (collectionsAdapter != null) {
                collectionsAdapter.notifyDataSetChanged();
            }
        }
        arrayList = new ArrayList<>();
    }



}
