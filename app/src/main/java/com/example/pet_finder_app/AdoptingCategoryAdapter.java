package com.example.pet_finder_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdoptingCategoryAdapter extends RecyclerView.Adapter<AdoptingCategoryAdapter.MyViewHolder>{
    private List<AdoptingCategoryDomain> listPet;
    private int layoutResourceId;
    private String statusPet;
//    private OnDetailButtonClickListener listener;

    public interface OnDetailButtonClickListener {
        void onDetailButtonClick(int position);
    }

    public interface OnDetailPetClickListener {
        void onDetailPetClick(int position);
    }
    private OnDetailPetClickListener onDetailPetClickListener;

    //    public AdoptingCategoryAdapter(List<AdoptingCategoryDomain> listPet, int layoutResourceId, OnDetailButtonClickListener listener){
        public AdoptingCategoryAdapter(List<AdoptingCategoryDomain> listPet, int layoutResourceId, String statusPet){
        this.listPet = listPet;
        this.layoutResourceId = layoutResourceId;
        this.statusPet = statusPet;
//        this.listener = listener;
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
            Picasso.get().load(pet.getImage_id()).into(holder.image_id);
        }
        if (holder.gender != null) {
            if(pet.getGender() == "male"){
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/male.png?alt=media&token=9326764f-5c4d-49ee-9b54-9cd6a6c5f418").into(holder.gender);
            }else{
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/female.png?alt=media&token=6be18497-fa44-4fc3-8b68-7ba80b622e75").into(holder.gender);
            }
        }
        if (holder.name != null) {
            holder.name.setText(pet.getName());
        }
        if (holder.location != null) {
            holder.location.setText(pet.getLocation());
        }
        if (holder.favorite != null) {
            if(pet.getFavorite() == "favorite"){
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/favorate.png?alt=media&token=d6fe3d4a-6035-4641-9ed4-a4fb54117d33").into(holder.favorite);
            }else{
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/non_favorate.png?alt=media&token=dc32f3e7-f8e1-496f-855d-2b1ee5898d64").into(holder.favorite);
            }
        }
        if (holder.status != null) {
            if(statusPet.equals("Adopt")){
                holder.status.setImageResource(R.drawable.adopt);
            }else{
                holder.status.setImageResource(R.drawable.mising);
            }
        }
        if (holder.breed != null) {
            holder.breed.setText(pet.getBreed());
        }
        if (holder.price != null) {
            holder.price.setText(pet.getPrice());
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
        private Button detail_pet;

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
