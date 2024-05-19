package com.example.pet_finder_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyPetAdapter extends RecyclerView.Adapter<MyPetAdapter.MyViewHolder>{
    private List<AdoptingCategoryDomain> listPet;
    private Context mContext;

    public MyPetAdapter(Context context, List<AdoptingCategoryDomain> listPet){
        mContext = context;
        this.listPet = listPet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_pet_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return listPet.size();
    }

    public void onBindViewHolder(@NonNull MyPetAdapter.MyViewHolder holder, int position) {
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
        if (holder.breed != null) {
            holder.breed.setText(pet.getBreed());
        }
        if (holder.price != null) {
            holder.price.setText(String.valueOf(pet.getPrice()));
        }
        if (holder.age != null) {
            holder.age.setText(String.valueOf(pet.getAge()));
        }
        if (holder.condition != null) {
            holder.condition.setText(pet.getCondition());
        }
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddingPetActivity.class);
                intent.putExtra("activity", "edit");
                intent.putExtra("idPet", pet.getIdPet());
                mContext.startActivity(intent);
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView image_id,gender,editBtn, deleteBtn;
        private TextView name, breed, price,age, condition;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            image_id = itemView.findViewById(R.id.catImg);
            gender = itemView.findViewById(R.id.genderImg);
            name = itemView.findViewById(R.id.nameCat);
            breed = itemView.findViewById(R.id.breed_value);
            price = itemView.findViewById(R.id.price_value);
            age = itemView.findViewById(R.id.age_value);
            condition = itemView.findViewById(R.id.status_value);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
