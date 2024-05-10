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
        holder.image_id.setImageResource(pet.getImage_id());
        holder.gender.setImageResource(pet.getGender());
        holder.name.setText(pet.getName());
        holder.status.setText(pet.getStatus());
        holder.appearance.setText(pet.getAppearance());
        holder.date.setText(pet.getDate());
        holder.age.setText(pet.getDate());
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
        private TextView name, status, appearance, date, age;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            image_id = itemView.findViewById(R.id.catImg);
            gender = itemView.findViewById(R.id.gender);
            name = itemView.findViewById(R.id.nameCat);
            status = itemView.findViewById(R.id.status_value);
            appearance = itemView.findViewById(R.id.apperance_value);
            date = itemView.findViewById(R.id.date_value);
            age = itemView.findViewById(R.id.age_value);
        }
    }
}
