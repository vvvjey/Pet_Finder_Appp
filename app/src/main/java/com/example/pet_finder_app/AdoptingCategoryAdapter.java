package com.example.pet_finder_app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.Class.FavoritePet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdoptingCategoryAdapter extends RecyclerView.Adapter<AdoptingCategoryAdapter.MyViewHolder> {
    private List<AdoptingCategoryDomain> listPet;
    private List<String> favoriteIdPet = new ArrayList<>();
    private int layoutResourceId;
    private String statusPet;
    private String idUser = "1";
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("FavoritePet");
    private boolean isFavorite = false;

    public interface OnDetailButtonClickListener {
        void onDetailButtonClick(int position);
    }

    public interface OnDetailPetClickListener {
        void onDetailPetClick(int position);
    }

    private OnDetailPetClickListener onDetailPetClickListener;

    public AdoptingCategoryAdapter(List<AdoptingCategoryDomain> listPet, int layoutResourceId, String statusPet) {
        this.listPet = listPet;
        this.layoutResourceId = layoutResourceId;
        this.statusPet = statusPet;
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
            String genderUrl = pet.getGender().equals("male") ?
                    "https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/male.png?alt=media&token=9326764f-5c4d-49ee-9b54-9cd6a6c5f418" :
                    "https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/female.png?alt=media&token=6be18497-fa44-4fc3-8b68-7ba80b622e75";
            Picasso.get().load(genderUrl).into(holder.gender);
        }

        if (holder.name != null) {
            holder.name.setText(pet.getName());
        }

        if (holder.location != null) {
            holder.location.setText(pet.getLocation());
        }
        isFavorite = false;
        holder.favorite.setImageResource(R.drawable.non_favorate); // default icon
        databaseReference.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FavoritePet favoritePet = snapshot.getValue(FavoritePet.class);
//                if(favoritePet != null){
//                    favoriteIdPet = favoritePet.getFavoritePet();
//                    for(String idPetF : favoriteIdPet){
//                        if(idPetF.equals(pet.getIdPet())){
//                            Log.d("Show favorite", "IdPet: " + pet.getIdPet());
//                            holder.favorite.setImageResource(R.drawable.favorate); // update to favorite icon
//                            isFavorite = true;
//                        }
//                    }
//                }
                if(favoritePet != null ){
                    Log.d("Show favorite", "IdPet: " + pet.getIdPet());
                    if (favoritePet.getFavoritePet().contains(pet.getIdPet())) {
                        holder.favorite.setImageResource(R.drawable.favorate); // update to favorite icon
                        isFavorite = true;
                        Log.d("Show favorite", "IdPet2: " + pet.getIdPet());
                    }
                else{
                        Log.d("Show favorite", "IdPet2: " + pet.getIdPet());
                        isFavorite = false;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.favorite.setImageResource(R.drawable.non_favorate); // default icon
                databaseReference.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        FavoritePet favoritePet = snapshot.getValue(FavoritePet.class);
//                if(favoritePet != null){
//                    favoriteIdPet = favoritePet.getFavoritePet();
//                    for(String idPetF : favoriteIdPet){
//                        if(idPetF.equals(pet.getIdPet())){
//                            Log.d("Show favorite", "IdPet: " + pet.getIdPet());
//                            holder.favorite.setImageResource(R.drawable.favorate); // update to favorite icon
//                            isFavorite = true;
//                        }
//                    }
//                }
                        if(favoritePet != null ){
                            Log.d("Show favorite", "IdPet: " + pet.getIdPet());
                            if (favoritePet.getFavoritePet().contains(pet.getIdPet())) {
                                holder.favorite.setImageResource(R.drawable.favorate); // update to favorite icon
                                isFavorite = true;
                                Log.d("Show favorite", "IdPet2: " + pet.getIdPet());
                            }
                            else{
                                Log.d("Show favorite", "IdPet2: " + pet.getIdPet());
                                isFavorite = false;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                databaseReference.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("Show favorite", "IdPet: " + pet.getIdPet() + isFavorite);
                        FavoritePet favoritePet = snapshot.getValue(FavoritePet.class);
                        if (favoritePet == null) {
                            favoriteIdPet.add(pet.getIdPet());
                            favoritePet = new FavoritePet(idUser, new ArrayList<>());
                            holder.favorite.setImageResource(R.drawable.favorate);
                        }
                        if (isFavorite) {
                            favoritePet.getFavoritePet().remove(pet.getIdPet());
                            holder.favorite.setImageResource(R.drawable.non_favorate);

                            isFavorite = false;
                        } else {
                            favoritePet.getFavoritePet().add(pet.getIdPet());
                            holder.favorite.setImageResource(R.drawable.favorate);
                            isFavorite = true;
                        }

                        databaseReference.child(idUser).setValue(favoritePet);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

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

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView image_id, gender, favorite, status;
        private TextView name, location, breed, price, age, date_adopt, ranking, condition;
        private Button detail_pet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            image_id = itemView.findViewById(R.id.catImg);
            gender = itemView.findViewById(R.id.genderImg);
            name = itemView.findViewById(R.id.nameCat);
            favorite = itemView.findViewById(R.id.favorateImg);
//            location = itemView.findViewById(R.id.location_text);
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
