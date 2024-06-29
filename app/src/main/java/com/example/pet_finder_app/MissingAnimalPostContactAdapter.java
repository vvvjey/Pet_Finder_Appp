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

import com.example.pet_finder_app.Class.MissingPetContact;

import java.util.List;

public class MissingAnimalPostContactAdapter extends RecyclerView.Adapter<MissingAnimalPostContactAdapter.MyViewHolder> {
    private List<MissingPetContact> listMissingContact;
    private Context mContext;
    public MissingAnimalPostContactAdapter(List<MissingPetContact> listMissingContact,Context context){
        this.listMissingContact = listMissingContact;
        this.mContext = context;
    }
    public MissingAnimalPostContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.missing_animal_post_contact_item, parent, false);
        return new MissingAnimalPostContactAdapter.MyViewHolder(view);
    }

    public int getItemCount() {
        return listMissingContact.size();
    }
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MissingPetContact contact = listMissingContact.get(position);
        holder.missingContactName.setText(contact.getName());
        holder.missingContactDescription.setText(contact.getDescription());
        holder.missingContactMessage.setText(contact.getMessage());
        holder.missingContactTime.setText(contact.getTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OwnerpageActivity.class);
                intent.putExtra("idUserPost", contact.getFromUserId());
                v.getContext().startActivity(intent);
            }
        });
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView missingContactName,missingContactDescription,missingContactMessage,missingContactTime;
        private ImageView missingContactGender;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            missingContactName = itemView.findViewById(R.id.missingContactName);
            missingContactDescription = itemView.findViewById(R.id.missingContactDescription);
            missingContactMessage = itemView.findViewById(R.id.missingContactMessage);
            missingContactGender = itemView.findViewById(R.id.missingContactGender);
            missingContactTime = itemView.findViewById(R.id.missingContactTime);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

}
