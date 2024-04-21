package com.example.pet_finder_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import java.util.List;
import android.os.Bundle;

public class AdoptStatusAdapter extends RecyclerView.Adapter<AdoptStatusAdapter.MyViewHolder> {

    private List<AdoptStatusItem> ListAdoptStatus;
    public AdoptStatusAdapter(List<AdoptStatusItem> ListAdoptStatus){
        this.ListAdoptStatus = ListAdoptStatus;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adopt_status_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return ListAdoptStatus.size();
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AdoptStatusItem pet = ListAdoptStatus.get(position);
        holder.image_id.setImageResource(pet.getImage_id());
        holder.name.setText(pet.getName());
        holder.size.setText(pet.getSize());
        holder.breed.setText(pet.getBreed());
        holder.date.setText(pet.getDate());
        holder.color.setText(pet.getColor());
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView image_id;
        private TextView name, size, breed, date, color;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            image_id = itemView.findViewById(R.id.cat_img);
            size = itemView.findViewById(R.id.size_text);
            name = itemView.findViewById(R.id.cat_name);
            breed = itemView.findViewById(R.id.breed_text);
            color = itemView.findViewById(R.id.color_text);
            date = itemView.findViewById(R.id.date);

        }
    }
}

