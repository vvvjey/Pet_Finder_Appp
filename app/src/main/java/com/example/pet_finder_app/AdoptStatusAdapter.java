package com.example.pet_finder_app;

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

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdoptStatusAdapter extends RecyclerView.Adapter<AdoptStatusAdapter.MyViewHolder> {

    private List<AdoptStatusItem> ListAdoptStatus;
    private Context mContext;

    public AdoptStatusAdapter(List<AdoptStatusItem> ListAdoptStatus, Context context){
        this.ListAdoptStatus = ListAdoptStatus;
        this.mContext = context;
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
        if (holder.image_id != null) {
            Picasso.get().load(pet.getImage_id()).into(holder.image_id);
        }
        holder.name.setText(pet.getName());
        holder.size.setText(pet.getSize());
        holder.breed.setText(pet.getBreed());
        holder.date.setText(pet.getDate());
        holder.color.setText(pet.getColor());
        if (holder.statusOrder != null) {
            holder.statusOrder.setText(pet.getStatusOrder());
        }

        // Set click listener for the item
        holder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailRequetAdoptActivity.class);
                intent.putExtra("idOrder" ,pet.getIdOrder());
                mContext.startActivity(intent);
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView image_id;
        private TextView name, size, breed, date, color;
        private Button detailBtn;
        private TextView statusOrder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            image_id = itemView.findViewById(R.id.cat_img);
            size = itemView.findViewById(R.id.size_text);
            name = itemView.findViewById(R.id.cat_name);
            breed = itemView.findViewById(R.id.breed_text);
            color = itemView.findViewById(R.id.color_text);
            date = itemView.findViewById(R.id.date);
            detailBtn = itemView.findViewById(R.id.detailButton);
            statusOrder = itemView.findViewById(R.id.statusOrder);
        }
    }
}
