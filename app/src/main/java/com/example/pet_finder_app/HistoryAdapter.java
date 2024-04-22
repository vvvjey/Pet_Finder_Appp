package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder>{
    private List<AdoptingCategoryDomain> listPet;
    private int layoutResourceId;
    private Context mContext;
    public interface OnDetailButtonClickListener {
        void onDetailButtonClick(int position);
    }

    public interface OnDetailPetClickListener {
        void onDetailPetClick(int position);
    }
    private OnDetailPetClickListener onDetailPetClickListener;

    public HistoryAdapter(Context context, List<AdoptingCategoryDomain> listPet, int layoutResourceId){
        mContext = context;
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
        if (holder.detail_pet != null){
            holder.detail_pet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, HistoryAdoptDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView image_id,gender;
        private TextView name, breed, price,age, date_adopt, ranking, condition;
        private Button detail_pet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            image_id = itemView.findViewById(R.id.catImg);
            gender = itemView.findViewById(R.id.genderImg);
            name = itemView.findViewById(R.id.nameCat);
            breed = itemView.findViewById(R.id.breed_value);
            price = itemView.findViewById(R.id.price_value);
            age = itemView.findViewById(R.id.age_value);
            date_adopt = itemView.findViewById(R.id.date_adopt);
            ranking = itemView.findViewById(R.id.ranking);
            condition = itemView.findViewById(R.id.status_value);
            detail_pet = itemView.findViewById(R.id.btn_detail_pet);
        }
    }
}
