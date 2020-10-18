package com.wallpaper.wallp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wallpaper.wallp.ModelClasses.TrendModel;
import com.wallpaper.wallp.R;
import com.wallpaper.wallp.mainwallpaperopen;

import java.util.ArrayList;

public class TrendAdapter extends RecyclerView.Adapter<TrendAdapter.ViewHolder> {


    private static final String Tag="RecyclerView";
    Context mcontext;
    private ArrayList<TrendModel> arrayList;
    //String linnk;

    public TrendAdapter(Context mcontext, ArrayList<TrendModel> arrayList) {
        this.mcontext = mcontext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TrendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpapercard , parent , false);








        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(arrayList.get(position).getCategory());
        Picasso.get()
                .load(arrayList.get(position).getWallpaperlink())
                .fit().centerCrop()
                .into(holder.imageView);
        final String linnk = arrayList.get(position).getWallpaperlink();




        holder.cardssss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mcontext, "->"+linnk, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), mainwallpaperopen.class);
                intent.putExtra("wallpaperid", linnk);
                v.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        CardView cardssss;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView=itemView.findViewById(R.id.mainwallimage);
            textView=itemView.findViewById(R.id.categoryid);


            cardssss = itemView.findViewById(R.id.cardview);



            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*Toast.makeText(v.getContext(), "Clicked ->" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    Intent my = new Intent(getApplicationContext(), mainimagepage.class);
                    startActivity(my);
                    finish();*//*
                    *//*Intent intent=new Intent(v.getContext(), mainwallpaperopen.class);
                    intent.putExtra("wallpaperid",linnk);
                    v.getContext().startActivity(intent);*//*
                    //Toast.makeText(v.getContext(), "..."+linnk, Toast.LENGTH_SHORT).show();

                }
            });*/


        }
    }



}
