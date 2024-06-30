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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RescueCategoryAdapter extends RecyclerView.Adapter<RescueCategoryAdapter.MyViewHolder>{
    private List<RescueCategoryDomain> listRescue;
    private Context context;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    public RescueCategoryAdapter(List<RescueCategoryDomain> listRescue,Context context){
        this.listRescue = listRescue;
        this.context = context;
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
        holder.location.setText(rescue.getAddress());
        holder.distance.setText(rescue.getDistance());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RescueDetailActivity.class);
//                Log.d("ShowIdPlace", rescue.getPlace_id());
                intent.putExtra("stationId", rescue.getPlace_id());
                context.startActivity(intent);
            }
        });
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
