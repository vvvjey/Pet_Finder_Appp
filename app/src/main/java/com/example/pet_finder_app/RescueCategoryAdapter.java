package com.example.pet_finder_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RescueCategoryAdapter extends RecyclerView.Adapter<RescueCategoryAdapter.MyViewHolder>{
    private List<RescueCategoryDomain> listRescue;

    public RescueCategoryAdapter(List<RescueCategoryDomain> listRescue){
        this.listRescue = listRescue;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rescue_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return listRescue.size();
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RescueCategoryDomain rescue = listRescue.get(position);
        holder.image_id.setImageResource(rescue.getImage_id());
        holder.name.setText(rescue.getName());
        holder.location.setText(rescue.getLocation());
        holder.distance.setText(rescue.getDistance());
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView image_id;
        private TextView name, location, distance;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            image_id = itemView.findViewById(R.id.rescue_pic);
            name = itemView.findViewById(R.id.rescue_name);
            location = itemView.findViewById(R.id.rescue_location);
            distance = itemView.findViewById(R.id.rescue_distance);
        }
    }
}
