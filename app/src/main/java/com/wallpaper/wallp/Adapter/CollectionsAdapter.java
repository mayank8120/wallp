package com.wallpaper.wallp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wallpaper.wallp.ModelClasses.CollectionsModel;
import com.wallpaper.wallp.ModelClasses.TrendModel;
import com.wallpaper.wallp.R;

import java.util.ArrayList;
/*

public class CollectionsAdapter {
}
*/









public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.ViewHolder> {


    private static final String Tag="RecyclerView";
    private Context mcontext;
    private ArrayList<CollectionsModel> arrayList;

    public CollectionsAdapter(Context mcontext, ArrayList<CollectionsModel> arrayList) {
        this.mcontext = mcontext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CollectionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.collectioncardgridlayout , parent , false);








        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(arrayList.get(position).getCategory());
        Picasso.get()
                .load(arrayList.get(position).getWallpaperlink())
                .fit().centerCrop()
                .into(holder.imageView);

        /*Glide.with(mcontext)
                .load(arrayList.get(position).getWallpaperlink())
                .into(holder.imageView);*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView=itemView.findViewById(R.id.maincollectimage);
            textView=itemView.findViewById(R.id.categoryidcollect);
        }
    }



}

