package com.example.pet_finder_app;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.pet_finder_app.Class.MissingPet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchingLostPetAdapter extends ArrayAdapter<MissingPet> {
    private Activity context;
    private ArrayList<MissingPet> missingPets;


    public SearchingLostPetAdapter(Activity context, int layoutID, ArrayList<MissingPet> missingPets){
        super(context, layoutID, missingPets);
        this.context = context;
        this.missingPets = missingPets;

    }
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.searching_lost_pet_item, null,
                            false);
            viewHolder = new ViewHolder();
            viewHolder.missingPetImg = convertView.findViewById(R.id.missingPetImg);
            viewHolder.missingPetName = convertView.findViewById(R.id.missingPetName);
            viewHolder.missingPetGender = convertView.findViewById(R.id.genderImg);
            viewHolder.missingPetStatus = convertView.findViewById(R.id.missingPetStatus);
            viewHolder.missingPetColor = convertView.findViewById(R.id.missingPetColor);
            viewHolder.typeMissing = convertView.findViewById(R.id.typeMissing);
            viewHolder.missingPetAge = convertView.findViewById(R.id.missingPetAge);
            viewHolder.missingPetSize = convertView.findViewById(R.id.missingPetSize);
            viewHolder.cardView = convertView.findViewById(R.id.cardView);
            viewHolder.missingPetRegisterDate = convertView.findViewById(R.id.missingPetRegisterDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MissingPet missingPet = missingPets.get(position);
        Picasso.get().load(missingPet.getImgUrl().get(0)).into(viewHolder.missingPetImg);

        viewHolder.missingPetName.setText(missingPet.getName());
        if(missingPet.getGender().equals("Male")){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/male.png?alt=media&token=9326764f-5c4d-49ee-9b54-9cd6a6c5f418").into(viewHolder.missingPetGender);
        }else{
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/female.png?alt=media&token=6be18497-fa44-4fc3-8b68-7ba80b622e75").into(viewHolder.missingPetGender);
        }
        if(missingPet.getTypeMissing().equals("Seen")){
            Log.d("TypeMissingValue", missingPet.getTypeMissing());
            viewHolder.typeMissing.setBackgroundColor(Color.parseColor("#13B654"));
        } else if(missingPet.getTypeMissing().equals("Protected")){
            viewHolder.typeMissing.setBackgroundColor(Color.parseColor("#D223447E"));
        } else {
            viewHolder.typeMissing.setBackgroundColor(Color.parseColor("#FF5E5E"));
        }
        viewHolder.typeMissing.setText(missingPet.getTypeMissing());
        viewHolder.missingPetColor.setText(missingPet.getColor());
        viewHolder.missingPetRegisterDate.setText(missingPet.getRegisterDate());
        viewHolder.missingPetAge.setText(missingPet.getAge());
        viewHolder.missingPetSize.setText(missingPet.getSize());
        viewHolder.missingPetStatus.setText(missingPet.getStatusMissing());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PetDetailActivity.class);
                intent.putExtra("idPet", missingPet.getIdPet());
                intent.putExtra("petName", missingPet.getName());
                intent.putExtra("petAge", missingPet.getAge());
                intent.putExtra("petSize", missingPet.getSize());
                intent.putExtra("petBreed", missingPet.getBreed());
                intent.putExtra("addressMissing", missingPet.getAddressMissing());
                intent.putExtra("petGender", missingPet.getGender());
                intent.putExtra("petColor", missingPet.getColor());
                intent.putExtra("petRegisterDate", missingPet.getRegisterDate());
                intent.putExtra("petMissingDate", missingPet.getDateMissing());
                intent.putExtra("petTypeMissing", missingPet.getTypeMissing());
//                intent.putExtra("petImageUrl", missingPet.getImgUrl().get(0));
                intent.putExtra("postUserId",missingPet.getPostUserId());
                intent.putExtra("requestPoster",missingPet.getRequestPosterMissing());
                intent.putExtra("desciptionPet",missingPet.getDescription());
                intent.putExtra("statusMissing",missingPet.getStatusMissing());
                Log.d("request posster missing",missingPet.getRequestPosterMissing());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    static class ViewHolder {
        CardView cardView;
        ImageView missingPetImg;
        TextView missingPetName;
//        TextView missingPetGender;
        ImageView missingPetGender;
        TextView missingPetStatus;
        TextView missingPetColor;
        TextView missingPetRegisterDate;
        TextView typeMissing;
        TextView missingPetAge;
        TextView missingPetSize;
        TextView namePoster;
        TextView addressPoster;
        TextView missingPetDescription;
        TextView requestPoster;
        TextView phonePoster;
        TextView gmailPoster;
        TextView locationMissingPet;
    }
}
