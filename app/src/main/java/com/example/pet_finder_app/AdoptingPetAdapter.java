package com.example.pet_finder_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class AdoptingPetAdapter extends RecyclerView.Adapter<AdoptingPetAdapter.MyViewHolder>{
    private List<AdoptingPetItem> listPet;
    private Context context;

    public AdoptingPetAdapter(List<AdoptingPetItem> listPet, Context context){
        this.listPet = listPet;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adopting_pet_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return listPet.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AdoptingPetItem pet = listPet.get(position);
        holder.age.setText(pet.getAge());
        holder.category.setText(pet.getCategoryId());
        holder.color.setText(pet.getColor());
        Log.d("getImgUrl", pet.getImgUrl());
        Picasso.get().load(pet.getGender()).into(holder.gender);
        Picasso.get().load(pet.getImgUrl()).into(holder.image_id);
        holder.name.setText(pet.getName());
        holder.date.setText(pet.getRegisterDate());
        holder.status.setText(pet.getStatus());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdoptingPetDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView image_id,gender;
        private TextView name, status, date, age, category, color;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            age = itemView.findViewById(R.id.age_value);
            category = itemView.findViewById(R.id.category);
            color = itemView.findViewById(R.id.color);
            gender = itemView.findViewById(R.id.gender);
            image_id = itemView.findViewById(R.id.catImg);
            name = itemView.findViewById(R.id.nameCat);
            date = itemView.findViewById(R.id.date_value);
            status = itemView.findViewById(R.id.status_value);
        }
    }
}