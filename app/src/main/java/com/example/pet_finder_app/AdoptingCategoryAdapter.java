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
        if (holder.image_id != null) {
            holder.image_id.setImageResource(pet.getImage_id());
        }
        if (holder.gender != null) {
            holder.gender.setImageResource(pet.getGender());
        }
        if (holder.name != null) {
            holder.name.setText(pet.getName());
        }
        if (holder.location != null) {
            holder.location.setText(pet.getLocation());
        }
        if (holder.favorite != null) {
            holder.favorite.setImageResource(pet.getFavorite());
        }
        if (holder.status != null) {
            holder.status.setImageResource(pet.getStatus());
        }
        if (holder.breed != null) {
            holder.breed.setText(pet.getBreed());
        }
        if (holder.price != null) {
            holder.price.setText(String.valueOf(pet.getPrice()));
        }
        if (holder.age != null) {
            holder.age.setText(String.valueOf(pet.getAge()));
        }
        if (holder.date_adopt != null) {
            holder.date_adopt.setText(pet.getDate_adopt());
        }
        if (holder.ranking != null) {
            holder.ranking.setText(pet.getRanking());
        }
        if (holder.condition != null) {
            holder.condition.setText(pet.getCondition());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView image_id,gender, favorite, status;
        private TextView name, location, breed, price,age, date_adopt, ranking, condition;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            image_id = itemView.findViewById(R.id.catImg);
            gender = itemView.findViewById(R.id.genderImg);
            name = itemView.findViewById(R.id.nameCat);
            favorite = itemView.findViewById(R.id.favorateImg);
            location = itemView.findViewById(R.id.location_text);
            status = itemView.findViewById(R.id.status);
            breed = itemView.findViewById(R.id.breed_value);
            price = itemView.findViewById(R.id.price_value);
            age = itemView.findViewById(R.id.age_value);
            date_adopt = itemView.findViewById(R.id.date_adopt);
            ranking = itemView.findViewById(R.id.ranking);
            condition = itemView.findViewById(R.id.status_value);
        }
    }
}
