package com.example.pet_finder_app;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.pet_finder_app.Class.MissingPet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MissingAnimalPostAdapter extends ArrayAdapter<MissingPet> {
    private Activity context;
    private ArrayList<MissingPet> missingPets;


    public MissingAnimalPostAdapter(Activity context, int layoutID, ArrayList<MissingPet> missingPets){
        super(context, layoutID, missingPets);
        this.context = context;
        this.missingPets = missingPets;

    }
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.missing_animal_post_item, null,
                            false);
            viewHolder = new ViewHolder();

            viewHolder.missingPetImg = convertView.findViewById(R.id.missingPetImg);
            viewHolder.missingPetName = convertView.findViewById(R.id.missingPetName);
            viewHolder.missingPetGender = convertView.findViewById(R.id.missingPetGender);
            viewHolder.missingPetStatus = convertView.findViewById(R.id.missingPetStatus);
//            viewHolder.missingPetColor = convertView.findViewById(R.id.missingPetColor);
            viewHolder.typeMissing = convertView.findViewById(R.id.typeMissing);
//            viewHolder.missingPetAge = convertView.findViewById(R.id.missingPetAge);
//            viewHolder.missingPetSize = convertView.findViewById(R.id.missingPetSize);
            viewHolder.cardView = convertView.findViewById(R.id.cardView);
            viewHolder.missingPetRegisterDate = convertView.findViewById(R.id.missingPetRegisterDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MissingPet missingPet = missingPets.get(position);
        Picasso.get().load(missingPet.getImgUrl().get(0)).into(viewHolder.missingPetImg);

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
//        viewHolder.missingPetColor.setText(missingPet.getColor());
        viewHolder.missingPetRegisterDate.setText(missingPet.getRegisterDate());
//        viewHolder.missingPetAge.setText(missingPet.getAge());
//        viewHolder.missingPetSize.setText(missingPet.getSize());
        viewHolder.missingPetStatus.setText(missingPet.getStatusMissing());
        viewHolder.editBtnMissing = convertView.findViewById(R.id.editBtnMissing);
        viewHolder.deleteBtnMissing = convertView.findViewById(R.id.deleteBtnMissing);
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
                intent.putExtra("petTypeMissing", missingPet.getTypeMissing());
                intent.putExtra("petImageUrl", missingPet.getImgUrl().get(0));
                intent.putExtra("postUserId",missingPet.getPostUserId());
                intent.putExtra("requestPoster",missingPet.getRequestPosterMissing());
                intent.putExtra("desciptionPet",missingPet.getDescription());
                intent.putExtra("statusMissing",missingPet.getStatusMissing());
                intent.putExtra("petMissingDate", missingPet.getDateMissing());

                context.startActivity(intent);
            }
        });
        viewHolder.editBtnMissing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FillInforAboutLostPet.class);
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
                intent.putExtra("petImageUrl", missingPet.getImgUrl().get(0));
                intent.putExtra("postUserId",missingPet.getPostUserId());
                intent.putExtra("requestPoster",missingPet.getRequestPosterMissing());
                intent.putExtra("desciptionPet",missingPet.getDescription());
                intent.putExtra("statusMissing",missingPet.getStatusMissing());
                intent.putExtra("isEditMode",true);

                context.startActivity(intent);

            }
        });
        viewHolder.deleteBtnMissing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Missing Pet Post")
                        .setMessage("Are you sure you want to delete this post?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference missingPetRef = FirebaseDatabase.getInstance().getReference().child("Missing pet");

                                missingPetRef.orderByChild("id").equalTo(missingPet.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d("ACC","ac");
                                        if (dataSnapshot.exists()) {
                                            Log.d("VCC", "ac");

                                            dataSnapshot.getRef().removeValue().addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "Post deleted successfully", Toast.LENGTH_SHORT).show();
                                                    // Check if the position is within the bounds of the ArrayList
                                                    if (position >= 0 && position < missingPets.size()) {
                                                        missingPets.remove(position);
                                                        notifyDataSetChanged();
                                                    } else {
                                                        Log.e("Error", "Invalid position to remove from missingPets");
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Failed to delete post", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            Log.d("NoData", "No matching data found");
                                            Toast.makeText(context, "No matching post found to delete", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.e("Error", "Error querying database", databaseError.toException());
                                        Toast.makeText(context, "Failed to delete post", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
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
        TextView namePoster;
        TextView addressPoster;
        TextView missingPetDescription;
        TextView requestPoster;
        TextView phonePoster;
        TextView gmailPoster;
        TextView locationMissingPet;
        ImageView editBtnMissing,deleteBtnMissing;

    }
}
