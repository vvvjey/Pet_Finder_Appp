package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdoptingCategoryAdapter extends RecyclerView.Adapter<AdoptingCategoryAdapter.MyViewHolder>{
    private List<AdoptingCategoryDomain> listPet;
    private int layoutResourceId;

    public AdoptingCategoryAdapter(List<AdoptingCategoryDomain> listPet, int layoutResourceId){
        this.listPet = listPet;
        this.layoutResourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return listPet.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AdoptingCategoryDomain pet = listPet.get(position);
        holder.image_id.setImageResource(pet.getImage_id());
        holder.gender.setImageResource(pet.getGender());
        holder.name.setText(pet.getName());
        holder.location.setText(pet.getLocation());
        holder.favorite.setImageResource(pet.getFavorite());
        holder.status.setImageResource(pet.getStatus());
    }

    public void changeSizeHolder(@NonNull MyViewHolder holder, int position) {
        AdoptingCategoryDomain pet = listPet.get(position);
        holder.image_id.setImageResource(pet.getImage_id());
        holder.gender.setImageResource(pet.getGender());
        holder.name.setText(pet.getName());
        holder.location.setText(pet.getLocation());
        holder.favorite.setImageResource(pet.getFavorite());
        holder.status.setImageResource(pet.getStatus());
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView image_id,gender, favorite, status;
        private TextView name, location;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            image_id = itemView.findViewById(R.id.catImg);
            gender = itemView.findViewById(R.id.genderImg);
            name = itemView.findViewById(R.id.nameCat);
            favorite = itemView.findViewById(R.id.favorateImg);
            location = itemView.findViewById(R.id.location_text);
            status = itemView.findViewById(R.id.status);
        }


    }
}
