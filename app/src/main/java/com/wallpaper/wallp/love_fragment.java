package com.wallpaper.wallp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class love_fragment extends Fragment {

    ImageView btnlike;


    public love_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_love_fragment, container, false);

        /*btnlike=v.findViewById(R.id.btnLike);
        btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                    btnlike.setImageResource(R.drawable.ic_baseline_favorite_24);


            }
        });*/


        return v;
    }

}
