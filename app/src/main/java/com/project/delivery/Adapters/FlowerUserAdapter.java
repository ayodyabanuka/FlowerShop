package com.project.delivery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.delivery.Model.flower;
import com.project.delivery.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FlowerUserAdapter extends RecyclerView.Adapter<FlowerUserAdapter.FlowerViewHolder> {


    Context context;


    ArrayList<flower> flowerList;


    public FlowerUserAdapter(Context context, ArrayList<flower> flowerList) {
        this.context = context;
        this.flowerList = flowerList;


    }

    @NonNull
    @Override
    public FlowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.flower_card_user, parent,false);
        return new FlowerViewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull FlowerViewHolder holder, int position) {

        flower flowers = flowerList.get(position);


        holder.name.setText(flowers.getName());
        holder.price.setText(flowers.getPrice());
        Picasso.get().load(flowers.getImageurl()).into(holder.flowerImage);

    }

    @Override
    public int getItemCount() {
        return flowerList.size();
    }

    public static class FlowerViewHolder extends RecyclerView.ViewHolder{


        TextView name,price;
        ImageView flowerImage;


        public FlowerViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.userflower_name);
            price = itemView.findViewById(R.id.userflower_price);
            flowerImage = itemView.findViewById(R.id.userflowerimage);
        }
    }


}
