package com.example.pet_finder_app;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
            viewHolder.missingPetGender = convertView.findViewById(R.id.missingPetGender);
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
        Picasso.get().load(missingPet.getImgUrl()).into(viewHolder.missingPetImg);

        viewHolder.missingPetName.setText(missingPet.getName());
        if (missingPet.getGender().equals("Male")) {
            viewHolder.missingPetGender.setText("♂");
        } else {
            viewHolder.missingPetGender.setText("♀");

        }
        if(missingPet.getTypeMissing().equals("Seen")){
            Log.d("TypeMissingValue", missingPet.getTypeMissing());
            viewHolder.typeMissing.setBackgroundColor(Color.parseColor("#C3F1863F"));
        } else if(missingPet.getTypeMissing().equals("Protected")){
            viewHolder.typeMissing.setBackgroundColor(Color.parseColor("#D223447E"));
        } else {
            viewHolder.typeMissing.setBackgroundColor(Color.parseColor("#BEEA8282"));
        }
        viewHolder.typeMissing.setText(missingPet.getTypeMissing());
        viewHolder.missingPetColor.setText(missingPet.getColor());
        viewHolder.missingPetRegisterDate.setText(missingPet.getRegisterDate());
        viewHolder.missingPetAge.setText(missingPet.getAge());
        viewHolder.missingPetSize.setText(missingPet.getSize());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Handle pet detail page
            }
        });
        return convertView;
    }
    static class ViewHolder {
        CardView cardView;
        ImageView missingPetImg;
        TextView missingPetName;
        TextView missingPetGender;
        TextView missingPetStatus;
        TextView missingPetColor;
        TextView missingPetRegisterDate;
        TextView typeMissing;
        TextView missingPetAge;
        TextView missingPetSize;
    }
}
